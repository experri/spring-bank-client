package com.example.bank.dao;

import com.example.bank.model.Customer;
import com.example.bank.model.Employer;

import java.util.List;

public class EmployerDAO implements DAO<Employer>{
    @Override
    public Employer save(Employer obj) {
        return null;
    }

    @Override
    public boolean delete(Employer obj) {
        return false;
    }

    @Override
    public void deleteAll(List<Employer> entities) {

    }

    @Override
    public void saveAll(List<Employer> entities) {

    }

    @Override
    public List<Employer> findAll() {
        return List.of();
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

    @Override
    public Employer getOne(long id) {
        return null;
    }
}
