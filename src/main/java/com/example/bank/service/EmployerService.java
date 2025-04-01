package com.example.bank.service;

import com.example.bank.model.Employer;
import com.example.bank.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;

    public Employer save(Employer employer) {
        return employerRepository.save(employer);
    }

    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    public Employer getEmployerById(Long id) {
        return employerRepository.findById(id).orElse(null);
    }

    public void deleteEmployer(Long id) {
        employerRepository.deleteById(id);
    }
}