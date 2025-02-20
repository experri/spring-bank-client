package com.example.bank.service;

import com.example.bank.dao.AccountDAO;
import com.example.bank.exception.CustomException;
import com.example.bank.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account save(Account account) {
        return accountDAO.save(account);
    }

    public boolean delete(long id) {
        boolean isDeleted = accountDAO.deleteById(id);

        if (!isDeleted) {
            throw new CustomException("Account not found");
        }

        return true;
    }

    public Account getByNumber(String number) {
        Account account = accountDAO.findByNumber(number);

        if (account == null) {
            throw new CustomException("Account not found");
        }

        return accountDAO.findByNumber(number);
    }

}
