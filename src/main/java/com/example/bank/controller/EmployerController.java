package com.example.bank.controller;

import com.example.bank.DTO.CustomerRequest;
import com.example.bank.DTO.CustomerResponse;
import com.example.bank.DTO.EmployerRequest;
import com.example.bank.DTO.EmployerResponse;
import com.example.bank.service.CustomerService;
import com.example.bank.service.EmployerService;
import com.example.bank.validation.PartialUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
@Validated
public class EmployerController {
    @Autowired
    private EmployerService employerService;
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<EmployerResponse>> getAll() {
        return ResponseEntity.ok(employerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerResponse> getById(@PathVariable long id) {
        return ResponseEntity.ok(employerService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid EmployerRequest employerRequest) {
        if (employerService.getEmployerByName(employerRequest.getName()) != null) {
            return ResponseEntity.badRequest().body("Employer already exists");
        }
        return ResponseEntity.status(201).body(employerService.save(employerRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteEmployer(@PathVariable Long id) {
        employerService.deleteEmployer(id);
    }

    @PostMapping("/{id}/add-customer")
    public ResponseEntity<Object> addCustomer(@PathVariable long id, @RequestBody @Validated(PartialUpdate.class) CustomerRequest customerRequest) {
        EmployerResponse employer = employerService.getById(id);
        if (customerRequest.getEmail() == null && customerRequest.getId() == 0) {
            return ResponseEntity.badRequest().body("Customer email or ID is mandatory");
        }
        CustomerResponse customer = null;
        if (customerRequest.getId() != 0) {
            customer = customerService.getById(customerRequest.getId());
        } else {
            customer = customerService.getByEmail(customerRequest.getEmail());
        }

        if (customer == null) {
            return ResponseEntity.badRequest().body("Customer not found");
        }

        customerService.addEmployerToCustomer(customer.getId(), employer.getId());

        return ResponseEntity.ok("Customer added to employer");
    }

}
