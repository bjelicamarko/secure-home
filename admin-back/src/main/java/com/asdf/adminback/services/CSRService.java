package com.asdf.adminback.services;

import com.asdf.adminback.exceptions.CSRException;
import com.asdf.adminback.models.CSR;

import java.util.List;

public interface CSRService {

    List<CSR> findAll();

    List<CSR> findAllVerified();

    CSR findOneById(Long id);

    CSR save(CSR csr) throws CSRException;

    CSR verify(String securityCode) throws CSRException;

    CSR findByEmail(String email);

    void delete(CSR csr);
}
