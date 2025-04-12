package com.example.bank.service;


import com.example.bank.DTO.AccountFacade;
import com.example.bank.DTO.AccountRequest;
import com.example.bank.DTO.AccountResponse;
import com.example.bank.exception.CustomException;
import com.example.bank.exception.NotFoundException;
import com.example.bank.model.Account;
import com.example.bank.model.Customer;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountFacade accountFacade;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository, AccountFacade accountFacade) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.accountFacade = accountFacade;
    }
    public List<AccountResponse> getAll() {
        return accountRepository.findAll().stream()
                .map(accountFacade::toResponse)
                .toList();
    }

    public AccountResponse getById(long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
        return accountFacade.toResponse(account);
    }

    private Account getByNumber(String number) {
        Optional<Account> account = accountRepository.findByAccountNumber(number);

        if (account.isEmpty()) {
            throw new CustomException("Account not found");
        }

        return account.orElse(null);
    }

    public AccountResponse addAccount(long customerId, AccountRequest accountRequest) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found"));
        Account account = accountFacade.toEntity(accountRequest);
        account.setCustomer(customer);
        Account savedAccount = accountRepository.save(account);
        return accountFacade.toResponse(savedAccount);
    }

    private Account changeBalance(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    public AccountResponse deposit(String accountNumber, double amount) {
        Account account = getByNumber(accountNumber);
        Account savedAccount = changeBalance(account, amount);
        return accountFacade.toResponse(savedAccount);
    }

    public AccountResponse withdraw(String accountNumber, double amount) {
        Account account = getByNumber(accountNumber);

        if (account.getBalance() < amount) {
            throw new NotFoundException("Account not found");
        }
        Account savedAccount = changeBalance(account, -1 * amount);

        return accountFacade.toResponse(savedAccount);
    }


    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Відправник не знайдений: " + fromAccountNumber));
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Отримувач не знайдений: " + toAccountNumber));
        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Недостатньо коштів на рахунку");
        }
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    public void delete(long id) {
        accountRepository.deleteById(id);
    }

}
