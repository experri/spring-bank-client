package com.example.bank.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends AbstractEntity{


    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private Integer age;
    @ManyToMany
    @JoinTable(
            name = "customer_employer",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "employer_id")
    )
    private Set<Employer> employers;

    @OneToMany(mappedBy = "customer")
    private Set<Account> accounts;

    public Customer() {}

    public Customer(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Employer> getEmployers() {
        return employers;
    }

    public void setEmployers(Set<Employer> employers) {
        this.employers = employers;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Customer customer))
            return false;

        return age == customer.getAge() &&
                customer.getName().equals(name) &&
                customer.getEmail().equals(email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, age);
    }

    @Override
    public String toString() {
        return "Customer { " +
                "name: '" + name + '\'' +
                ", email: '" + email + '\'' +
                ", age: " + age +
                ", accounts: " + accounts +
                " }";
    }
}
