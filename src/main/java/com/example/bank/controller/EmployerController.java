package com.example.bank.controller;

import com.example.bank.DTO.CustomerRequest;
import com.example.bank.DTO.CustomerResponse;
import com.example.bank.DTO.EmployerRequest;
import com.example.bank.DTO.EmployerResponse;
import com.example.bank.service.CustomerService;
import com.example.bank.service.EmployerService;
import com.example.bank.util.ResponseHandler;
import com.example.bank.validation.FullUpdate;
import com.example.bank.validation.PartialUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
        List<EmployerResponse> employers = employerService.getAll();

        log.info("Getting {} employers", employers.size());

        return ResponseEntity.ok(employers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerResponse> getById(@PathVariable long id) {
        EmployerResponse employer = employerService.getById(id);

        log.info(
                "Getting employer with ID {} and name {}",
                id,
                employer.getName()
        );

        return ResponseEntity.ok(employer);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Validated(FullUpdate.class) EmployerRequest employerRequest) {
        if (employerService.getEmployerByName(employerRequest.getName()) != null) {
            log.info(
                    "Employer with name: {} can't be created. It already exists.",
                    employerRequest.getName()
            );

            return ResponseHandler.generateResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "Employer already exists",
                    null
            );
        }
        EmployerResponse employer = employerService.save(employerRequest);

        log.info(
                "Employer with name {} and ID {} created successfully",
                employer.getName(),
                employer.getId()
        );

        return ResponseEntity.status(201).body(employer);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployer(@PathVariable Long id) {
        employerService.deleteEmployer(id);
    }

    @PostMapping("/{id}/add-customer")
    public ResponseEntity<Object> addCustomer(@PathVariable long id, @RequestBody @Validated(PartialUpdate.class) CustomerRequest customerRequest) {
        EmployerResponse employer = employerService.getById(id);
        if (customerRequest.getEmail() == null && customerRequest.getId() == 0) {
            log.info("Customer email or ID is mandatory when adding a customer to an employer");
            return ResponseHandler.generateResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "Customer email or ID is mandatory",
                    null
            );
        }
        CustomerResponse customer = null;
        if (customerRequest.getId() != 0) {
            customer = customerService.getById(customerRequest.getId());
        } else {
            customer = customerService.getByEmail(customerRequest.getEmail());
        }

        if (customer == null) {
            log.info("Customer not found when adding a customer to an employer");
            return ResponseHandler.generateResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "Customer not found",
                    null
            );
        }

        customerService.addEmployerToCustomer(customer.getId(), employer.getId());

        log.info(
                "Customer with ID {} added to employer with ID {}",
                customer.getId(),
                employer.getId()
        );

        return ResponseHandler.generateResponse(
                HttpStatus.OK,
                false,
                "Customer added to employer",
                null
        );
    }

}
