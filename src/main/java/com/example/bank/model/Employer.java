package com.example.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employers")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Employer extends AbstractEntity {
    @Column(nullable = false)
    private String name;

    private String address;

    @JsonIgnore
    @ManyToMany(mappedBy = "employers")
    private Set<Customer> customers = new HashSet<>();


    public Employer(String name, String address) {
        this.name = name;
        this.address = address;
    }

}
