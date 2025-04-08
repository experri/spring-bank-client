package com.example.bank.service;

import com.example.bank.DTO.CustomerFacade;
import com.example.bank.DTO.CustomerRequest;
import com.example.bank.DTO.CustomerResponse;
import com.example.bank.exception.CustomException;
import com.example.bank.model.Account;
import com.example.bank.model.Customer;
import com.example.bank.model.Employer;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.CustomerRepository;
import com.example.bank.repository.EmployerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerFacade customerFacade;
    private final EmployerRepository employerRepository;
    private AccountRepository accountRepository;

    public CustomerService(CustomerRepository customerRepository, CustomerFacade customerFacade, EmployerRepository employerRepository) {
        this.customerRepository = customerRepository;
        this.customerFacade = customerFacade;
        this.employerRepository = employerRepository;
    }

    public CustomerResponse getById(long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomException("Customer not found"));

        return customerFacade.toResponse(customer);
    }

    public CustomerResponse getByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);

        if (customer.isEmpty()) {
            return null;
        }

        return customerFacade.toResponse(customer.orElse(null));
    }

    public Page<CustomerResponse> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(customerFacade::toResponse);
    }

    public CustomerResponse save(CustomerRequest customerRequest) {
        Customer customer = customerFacade.toEntity(customerRequest);
        Customer savedCustomer = customerRepository.save(customer);

        return customerFacade.toResponse(savedCustomer);
    }


    public CustomerResponse update(long id, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomException("Customer not found"));

        if (customerRequest.getEmail() != null) {
            customer.setEmail(customerRequest.getEmail());
        }

        if (customerRequest.getPassword() != null) {
            customer.setPassword(customerRequest.getPassword());
        }

        if (customerRequest.getName() != null) {
            customer.setName(customerRequest.getName());
        }

        if (customerRequest.getAge() != null) {
            customer.setAge(customerRequest.getAge());
        }

        if (customerRequest.getPhone() != null) {
            customer.setPhone(customerRequest.getPhone());
        }

        Customer savedCustomer = customerRepository.save(customer);

        return customerFacade.toResponse(savedCustomer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Account createAccountForCustomer(Long customerId, Account account) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
        account.setCustomer(customer);
        Account savedAccount = accountRepository.save(account);
        customer.getAccounts().add(savedAccount);
        customerRepository.save(customer);
        return savedAccount;
    }

    public void deleteAccountForCustomer(Long customerId, Long accountId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
        Account accountToRemove = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Customer not found with id: " + accountId));
        customer.getAccounts().remove(accountToRemove);
        accountRepository.delete(accountToRemove);
    }

    public void addEmployerToCustomer(long customerId, long employerId) {
    Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomException("Customer not found"));
    Employer employer = employerRepository.findById(employerId).orElseThrow(() -> new CustomException("Employer not found"));

         customer.getEmployers().add(employer);
         customerRepository.save(customer);
}

}
