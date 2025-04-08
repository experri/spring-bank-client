package com.example.bank.DTO;

import lombok.Data;

@Data
public class AccountRequest {
    private long id;
    private String number;
    private String currency;
    private double balance;
    private double amount;
}
