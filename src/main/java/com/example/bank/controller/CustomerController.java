package com.example.bank.controller;


import com.example.bank.dto.AccountDTO;
import com.example.bank.dto.CustomerDTO;
import com.example.bank.entity.MessageResponse;
import com.example.bank.model.Account;
import com.example.bank.model.Customer;
import com.example.bank.service.AccountService;
import com.example.bank.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")

public class CustomerController {
    private final CustomerService customerService;
    private final AccountService accountService;

    public CustomerController(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }


    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
       List<CustomerDTO> customers = customerService.getAll().stream().map(customer -> {
           CustomerDTO customerDTO = new CustomerDTO();

           customerDTO.setId(customer.getId());
           customerDTO.setName(customer.getName());
           customerDTO.setEmail(customer.getEmail());
           customerDTO.setAge(customer.getAge());

           return customerDTO;
       }).toList();

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long id) {

        Customer customer = customerService.getById(id);

        return ResponseEntity.ok(customer);
    }

    @PostMapping("/create")
    public  ResponseEntity<Object> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO.getName(), customerDTO.getEmail(), customerDTO.getAge());

        return ResponseEntity.status(201).body(customerService.save(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable long id, @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerService.getById(id);

        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setAge(customerDTO.getAge());

        return ResponseEntity.ok(customerService.save(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable long id) {
        customerService.delete(id);

        return ResponseEntity.ok("Customer deleted");
    }

    @PostMapping("/{id}/accounts")
    public ResponseEntity<Object> addAccount(@PathVariable long id, @RequestBody AccountDTO accountDTO) {
        Customer customer = customerService.getById(id);
        Account account = new Account(accountDTO.getCurrency(), customer);

        accountService.save(account);

        return ResponseEntity.ok(customerService.addAccount(id, account));
    }
    @DeleteMapping("/{id}/accounts/{accountId}")
    public ResponseEntity<Object> deleteAccount(@PathVariable long id, @PathVariable long accountId) {
        customerService.deleteAccount(id, accountId);
        accountService.delete(accountId);

        return ResponseEntity.ok(new MessageResponse("Account deleted"));
    }

}
