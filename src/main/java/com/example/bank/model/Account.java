package com.example.bank.model;

import com.example.bank.enums.Currency;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class Account {

   private Long id = IdGen.generateAccountId();

   private String number = UUID.randomUUID().toString();

   private Currency currency;
   private Double balance = 0.0;

    @JsonBackReference
    private Customer customer;

    public Account() {}

    public Account(Currency currency, Customer customer) {
        this.currency = currency;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Account account))
            return false;

        return account.getBalance() == balance &&
                account.getId() == id &&
                account.getNumber().equals(number) &&
                account.getCurrency().equals(currency) &&
                account.getCustomer().equals(customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, currency, balance, customer);
    }

    @Override
    public String toString() {
        return "Account { " +
                "number: '" + number + '\'' +
                ", currency: " + currency.name() +
                ", balance: " + balance +
                ", customer name: " + customer.getName() +
                ", customer email: " + customer.getEmail() +
                " }";
    }
}
