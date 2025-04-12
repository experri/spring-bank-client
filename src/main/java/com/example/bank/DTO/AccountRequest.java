package com.example.bank.DTO;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private long id;
    private String number;
    private String currency;
    private double balance;
    private double amount;
}
