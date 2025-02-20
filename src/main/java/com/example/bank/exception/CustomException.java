package com.example.bank.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}