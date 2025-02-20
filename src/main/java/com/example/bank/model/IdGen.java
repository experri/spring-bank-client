package com.example.bank.model;

public class IdGen {
    private static long customerId = 0L;
    private static long accountId = 0L;

    public static long generateCustomerId() {
        return ++customerId;
    }

    public static long generateAccountId() {
        return ++accountId;
    }
}
