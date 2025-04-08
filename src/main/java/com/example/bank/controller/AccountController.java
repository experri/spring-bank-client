package com.example.bank.controller;


import com.example.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String accountNumber, @RequestParam("amount") double amount) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Сума повинна бути більшою за 0");
        }
        accountService.deposit(accountNumber, amount);
        return ResponseEntity.ok("Рахунок поповнено на " + amount);
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String accountNumber, @RequestParam double amount) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Сума повинна бути більшою за 0");
        }
        try {
            accountService.withdraw(accountNumber, amount);
            return ResponseEntity.ok("З рахунку знято " + amount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
}
