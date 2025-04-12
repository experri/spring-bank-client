package com.example.bank.DTO;


import lombok.Data;

@Data
public class RegisterResponse {
    private long id;
    private String username;
    private String email;
    private String token;
}