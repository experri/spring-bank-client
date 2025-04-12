package com.example.bank.controller;



import com.example.bank.DTO.AccountRequest;
import com.example.bank.DTO.CustomerRequest;
import com.example.bank.DTO.CustomerResponse;
import com.example.bank.service.AccountService;
import com.example.bank.service.CustomerService;
import com.example.bank.util.ResponseHandler;
import com.example.bank.validation.FullUpdate;
import com.example.bank.validation.PartialUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;
    private final AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(customerService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Validated(FullUpdate.class) CustomerRequest customerRequest) {
        if (customerRequest.getName() == null || customerRequest.getEmail() == null || customerRequest.getAge() == 0) {
            return ResponseHandler.generateResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "Customer name, email and age are mandatory",
                    null
            );
        }
        if (customerService.getByEmail(customerRequest.getEmail()) != null) {
            return ResponseHandler.generateResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "Email already exists",
                    null
            );
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customerRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable long id, @RequestBody @Validated(PartialUpdate.class) CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.update(id, customerRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseHandler.generateResponse(
                HttpStatus.OK,
                false,
                "Customer deleted",
                null
        );
    }

    @PostMapping("/{id}/accounts")
    public ResponseEntity<Object> addAccount(@PathVariable("id") long customerId, @RequestBody AccountRequest accountRequest) {
        if (accountRequest.getCurrency() == null) {
            return ResponseHandler.generateResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "Currency is mandatory",
                    null
            );
        }

        return ResponseEntity.ok(accountService.addAccount(customerId, accountRequest));
    }

    @DeleteMapping("/{id}/accounts/{accountId}")
    public ResponseEntity<Object> deleteAccountForCustomer(@PathVariable Long id, @PathVariable Long accountId) {
        customerService.deleteAccountForCustomer(id, accountId);
        return ResponseHandler.generateResponse(
                HttpStatus.OK,
                false,
                "Account deleted",
                null
        );
    }

}
