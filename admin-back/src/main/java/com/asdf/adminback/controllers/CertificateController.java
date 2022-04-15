package com.asdf.adminback.controllers;

import com.asdf.adminback.dto.CertificateSigningDTO;
import com.asdf.adminback.exceptions.CSRException;
import com.asdf.adminback.models.CSR;
import com.asdf.adminback.services.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @PostMapping()
    public ResponseEntity<String> save(@RequestBody CertificateSigningDTO certificateSigningDTO) {
        return null;
    }

}
