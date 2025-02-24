package com.example.bank.controller;

import com.example.bank.dto.Transfer;
import com.example.bank.entity.MessageResponse;
import com.example.bank.model.Account;
import com.example.bank.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PatchMapping("/transfer")
    public ResponseEntity<Object> transfer(@RequestBody Transfer transferDTO) {
        Account accountFrom = accountService.getByNumber(transferDTO.getFromNumber());
        Account accountTo = accountService.getByNumber(transferDTO.getToNumber());

        if (accountFrom.getBalance() < transferDTO.getAmount()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new MessageResponse("Not enough money"));
        }

        accountFrom.setBalance(accountFrom.getBalance() - transferDTO.getAmount());
        accountTo.setBalance(accountTo.getBalance() + transferDTO.getAmount());

        accountService.save(accountFrom);
        accountService.save(accountTo);

        return ResponseEntity.ok(new MessageResponse("Transfer successful"));
    }


    @PatchMapping("/{number}/deposit") public ResponseEntity<Account> deposit(@PathVariable String number, @RequestParam("amount") double amount) {
        Account account = accountService.getByNumber(number);

        account.setBalance(account.getBalance() + amount);

        return ResponseEntity.ok(accountService.save(account));
    }

    @PatchMapping("/{number}/withdraw")
    public ResponseEntity<Object> withdraw(@PathVariable String number, @RequestParam("amount") double amount) {
        Account account = accountService.getByNumber(number);

        if (account.getBalance() < amount) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new MessageResponse("Not enough money"));
        }

        account.setBalance(account.getBalance() - amount);

        return ResponseEntity.ok(accountService.save(account));
    }
}
