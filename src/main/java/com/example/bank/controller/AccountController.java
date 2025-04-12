package com.example.bank.controller;


import com.example.bank.DTO.AccountRequest;
import com.example.bank.service.AccountService;
import com.example.bank.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<Object> deposit(@PathVariable String accountNumber, @RequestParam("amount") double amount) {
        if (amount <= 0) {
            return ResponseHandler.generateResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "Amount should be greater than 0",
                    null
            );
        }

        return ResponseEntity.ok(accountService.deposit(accountNumber, amount));
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<Object> withdraw(@PathVariable String number, @RequestParam("amount") double amount) {
        if (amount <= 0) {
            return ResponseHandler.generateResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "Amount should be greater than 0",
                    null
            );
        }
        return ResponseEntity.ok(accountService.withdraw(number, amount));

    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, @RequestParam double amount) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Сума повинна бути більшою за 0");
        }
        try {
            accountService.transfer(fromAccountNumber, toAccountNumber, amount);
            return ResponseEntity.ok("Переказано " + amount + " з рахунку " + fromAccountNumber + " на рахунок " + toAccountNumber);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{number}/transfer")
    public ResponseEntity<Object> transfer(@PathVariable String number, @RequestBody AccountRequest accountRequest) {
        double amount = accountRequest.getAmount();

        if (amount <= 0) {
            return ResponseHandler.generateResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "Amount should be greater than 0",
                    null
            );
        }

        accountService.withdraw(number, amount);
        accountService.deposit(accountRequest.getNumber(), amount);
        return ResponseHandler.generateResponse(
                HttpStatus.OK,
                false,
                "Transfer successful",
                null
        );
    }
}

