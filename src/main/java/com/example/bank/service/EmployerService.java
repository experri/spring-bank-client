package com.example.bank.service;

import com.example.bank.DTO.EmployerFacade;
import com.example.bank.DTO.EmployerRequest;
import com.example.bank.DTO.EmployerResponse;
import com.example.bank.exception.CustomException;
import com.example.bank.exception.NotFoundException;
import com.example.bank.model.Employer;
import com.example.bank.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;
    private final EmployerFacade employerFacade;

    public EmployerService(EmployerRepository employerRepository, EmployerFacade employerFacade) {
        this.employerRepository = employerRepository;
        this.employerFacade = employerFacade;
    }

    public EmployerResponse save(EmployerRequest employer) {
        Employer savedEmployer = employerRepository.save(employerFacade.toEntity(employer));

        return employerFacade.toResponse(savedEmployer);
    }

    public List<EmployerResponse> getAll() {
        return employerRepository.findAll()
                .stream()
                .map(employerFacade::toResponse)
                .toList();
    }

    public EmployerResponse getById(long id) {
        Employer employer = employerRepository.findById(id).orElseThrow(() -> new NotFoundException("Employer hasn't been found!"));

        return employerFacade.toResponse(employer);
    }

    public EmployerResponse getEmployerByName(String name) {
        Optional<Employer> employer = employerRepository.findByName(name);

        if (employer.isEmpty()) {
            return null;
        }

        return employerFacade.toResponse(employer.orElse(null));
    }

    public void deleteEmployer(Long id) {
        employerRepository.deleteById(id);
    }
}