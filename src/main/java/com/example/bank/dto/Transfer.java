package com.example.bank.dto;

public class Transfer {
    private String fromNumber;
    private String toNumber;
    private double amount;

    public Transfer(String fromNumber, String toNumber, double amount) {
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        this.amount = amount;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
