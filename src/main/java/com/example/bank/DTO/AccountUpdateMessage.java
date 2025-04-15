package com.example.bank.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AccountUpdateMessage {
    private final String accountNumber;
    private final double newBalance;
}