package com.example.bank.dao;

import com.example.bank.model.Customer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomerDAO implements DAO<Customer> {

    private static DAO<Customer> customerDAO = null;
    private final Map<Long, Customer> customers = new HashMap<>();


    public static DAO<Customer> getInstance() {
        if (customerDAO == null) {
            customerDAO = new CustomerDAO();
        }

        return customerDAO;
    }


    @Override
    public Customer save(Customer obj) {
        customers.put(obj.getId(), obj);

        return obj;
    }

    @Override
    public boolean delete(Customer obj) {
        return customers.remove(obj.getId()) != null;
    }

    @Override
    public void deleteAll(List<Customer> entities) {
        entities.forEach(customer -> customers.remove(customer.getId()));

    }

    @Override
    public void saveAll(List<Customer> entities) {
        entities.forEach(customer -> customers.put(customer.getId(), customer));
    }

    @Override
    public List<Customer> findAll() {
        return customers.values().stream().toList();
    }

    @Override
    public boolean deleteById(long id) {
        return customers.remove(id) != null;
    }

    @Override
    public Customer getOne(long id) {
        return customers.get(id);
    }

    public Customer findByEmail(String email) {
        return customers.values()
                        .stream()
                        .filter(customer -> customer.getEmail().equals(email))
                        .findFirst()
                        .orElse(null);
    }
}
