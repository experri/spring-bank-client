package com.example.bank.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends AbstractEntity {
    @NotBlank(message = "User email is mandatory")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "User password is mandatory")
    private String password;

    @NotBlank(message = "User username is mandatory")
    private String username;
}