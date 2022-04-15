package com.asdf.adminback.controllers;

import com.asdf.adminback.dto.CertificateSigningDTO;
import com.asdf.adminback.exceptions.CertificateSigningDTOException;
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
        try {
            certificateService.createAndWriteLeafCertificate(certificateSigningDTO);
            return new ResponseEntity<>("Certificate successfully created.", HttpStatus.OK);
        }
        catch (CertificateSigningDTOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Unknown exception happened while creating certificate", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
