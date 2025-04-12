package com.example.bank.service;

import com.example.bank.DTO.AccountFacade;
import com.example.bank.DTO.AccountRequest;
import com.example.bank.DTO.AccountResponse;
import com.example.bank.enums.Currency;
import com.example.bank.exception.CustomException;
import com.example.bank.exception.NotFoundException;
import com.example.bank.model.Account;
import com.example.bank.model.Customer;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountFacade accountFacade;

    @InjectMocks
    private AccountService accountService;

    private AccountRequest accountRequest;
    private AccountResponse accountResponse;
    private Account account;

    private final String number = "1234567890";
    private final Currency currency = Currency.USD;

    @BeforeEach
    void setUp() {
        accountRequest = new AccountRequest(1L, number, currency.toString(), 0, 0);
        accountResponse = new AccountResponse(1L, number, currency, 0);

        account = new Account();
        account.setId(1L);
    }

    @Test
    void testGetAll() {
        when(accountRepository.findAll()).thenReturn(List.of(account));
        when(accountFacade.toResponse(account)).thenReturn(accountResponse);

        List<AccountResponse> result = accountService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(accountResponse, result.get(0));
    }

    @Test
    void testGetByIdFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountFacade.toResponse(account)).thenReturn(accountResponse);

        AccountResponse result = accountService.getById(1L);

        assertNotNull(result);
        assertEquals(accountResponse, result);
    }

    @Test
    void testGetByIdNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            accountService.getById(1L);
        });
    }

    @Test
    void testAddAccount() {
        Customer customer = new Customer();
        customer.setId(1L);
        account.setCustomer(customer);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(accountFacade.toEntity(accountRequest)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountFacade.toResponse(account)).thenReturn(accountResponse);

        AccountResponse result = accountService.addAccount(1L, accountRequest);

        assertNotNull(result);
        assertEquals(accountResponse, result);
    }

    @Test
    void testAddAccountCustomerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            accountService.addAccount(1L, accountRequest);
        });
    }

    @Test
    void testDelete() {
        long accountId = 1L;

        willDoNothing().given(accountRepository).deleteById(accountId);

        accountService.delete(accountId);

        verify(accountRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDepositSuccess() {
        double amount = 100.50;
        double expectedBalance = account.getBalance() + amount;

        when(accountRepository.findByAccountNumber(number)).thenReturn(Optional.ofNullable(account));

        account.setBalance(account.getBalance() + amount);
        when(accountRepository.save(account)).thenReturn(account);

        accountResponse.setBalance(account.getBalance());
        when(accountFacade.toResponse(account)).thenReturn(accountResponse);

        AccountResponse result = accountService.deposit(number, amount);

        assertNotNull(result);
        assertEquals(expectedBalance, result.getBalance());
    }

    @Test
    void testDepositAccountNotFound() {
        double amount = 100.50;

        when(accountRepository.findByAccountNumber(number)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> {
            accountService.deposit(number, amount);
        });
    }

    @Test
    void testWithdrawSuccess() {
        double amount = 50.20;
        double expectedBalance;

        account.setBalance(100);
        expectedBalance = account.getBalance() - amount;

        when(accountRepository.findByAccountNumber(number)).thenReturn(Optional.ofNullable(account));
        when(accountRepository.save(account)).thenReturn(account);

        accountResponse.setBalance(expectedBalance);
        when(accountFacade.toResponse(account)).thenReturn(accountResponse);

        AccountResponse result = accountService.withdraw(number, amount);

        assertNotNull(result);
        assertEquals(expectedBalance, result.getBalance());
    }

    @Test
    void testWithdrawNotEnoughMoney() {
        double amount = 70;

        account.setBalance(50);
        when(accountRepository.findByAccountNumber(number)).thenReturn(Optional.ofNullable(account));

        assertThrows(CustomException.class, () -> {
            accountService.withdraw(number, amount);
        });
    }

    @Test
    void testWithdrawAccountNotFound() {
        double amount = 50.20;

        when(accountRepository.findByAccountNumber(number)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> {
            accountService.withdraw(number, amount);
        });
    }
}