package com.asdf.adminback.services;

import com.asdf.adminback.exceptions.CSRException;
import com.asdf.adminback.models.CSR;
import com.asdf.adminback.repositories.CSRRepository;
import com.asdf.adminback.util.CSRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CSRServiceImpl implements CSRService {

    @Autowired
    private CSRRepository csrRepository;

    @Override
    public List<CSR> findAll() {
        return csrRepository.findAll();
    }

    @Override
    public List<CSR> findAllVerified() {
        return csrRepository.findAllVerified();
    }

    @Override
    public CSR findOneById(Long id) {
        return csrRepository.findOneById(id);
    }

    @Override
    public CSR save(CSR csr) throws CSRException {
        CSRUtils.checkBasicCSRInfo(csr);
        csr.setVerified(false);

        try {
            csr = csrRepository.save(csr);
        }
        catch (DataIntegrityViolationException e) {
            throw new CSRException("Email or common name already exist in database.");
        }

        // Dodati kod za slanje mejla za verifikaciju

        return csr;
    }

    @Override
    public void verify(Long id) throws CSRException {
        CSR csr = csrRepository.findOneById(id);

        if(csr == null)
            throw new CSRException("Invalid csr id for verification.");

        csr.setVerified(true);
        csrRepository.save(csr);
    }
}
