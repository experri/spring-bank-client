package com.example.bank.service;

import com.example.bank.dao.AccountDAO;
import com.example.bank.dao.CustomerDAO;
import com.example.bank.exception.CustomException;
import com.example.bank.model.Account;
import com.example.bank.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final AccountDAO accountDao;
    private final CustomerDAO customerDao;

    public CustomerService(AccountDAO accountDao, CustomerDAO customerDao) {
        this.accountDao = accountDao;
        this.customerDao = customerDao;
    }

    public Customer save(Customer customer) {
        return customerDao.save(customer);
    }

    public List<Customer> getAll() {
        return customerDao.findAll();
    }

    public Customer getById(long id) {
        Customer customer = customerDao.getOne(id);

        if (customer == null) {
            throw new CustomException("Customer not found");
        }

        return customer;
    }

    public Customer getByEmail(String email) {
        return customerDao.findByEmail(email);
    }

    public boolean delete(Long id) {
        boolean isDeleted = customerDao.deleteById(id);

        if (!isDeleted) {
            throw new CustomException("Customer not found");
        }

        return true;
    }

    public Customer addAccount(Long customerId, Account account) {
        Customer customer = customerDao.getOne(customerId);

        if (customer == null) {
            throw new CustomException("Customer not found");
        }

        customer.getAccounts().add(account);

        return customer;
    }

    public void deleteAccount(Long customerId, Long accountId) {
        Customer customer = customerDao.getOne(customerId);

        if (customer == null) {
            throw new CustomException("Customer not found");
        }

        Account account = accountDao.getOne(accountId);

        if (account == null) {
            throw new CustomException("Account not found");
        }

        customer.getAccounts().remove(account);
    }
}
