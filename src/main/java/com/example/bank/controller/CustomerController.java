package com.example.bank.controller;



import com.example.bank.model.Account;
import com.example.bank.model.Customer;
import com.example.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        System.out.println("Customer: " + customer.getName());
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        Customer customer = customerService.updateCustomer(id, updatedCustomer);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Користувача видалено");
    }

    @PostMapping("/{id}/accounts")
    public ResponseEntity<Account> createAccountForCustomer(@PathVariable Long id, @RequestBody Account account) {
        Account createdAccount = customerService.createAccountForCustomer(id, account);
        return ResponseEntity.ok(createdAccount);
    }

    @DeleteMapping("/{id}/accounts/{accountId}")
    public ResponseEntity<String> deleteAccountForCustomer(@PathVariable Long id, @PathVariable Long accountId) {
        customerService.deleteAccountForCustomer(id, accountId);
        return ResponseEntity.ok("Рахунок видалено");
    }

}
