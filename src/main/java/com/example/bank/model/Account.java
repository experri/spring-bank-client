package com.example.bank.model;

import com.example.bank.enums.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;


@Table (name = "accounts")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractEntity {

    @UuidGenerator
    private String accountNumber;

    @Column(columnDefinition = "double default 0")
    private double balance;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private Currency currency;


    public Account(Currency currency, Customer customer) {
        this.currency = currency;
        this.customer = customer;
    }
}

