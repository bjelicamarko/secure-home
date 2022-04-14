package com.asdf.adminback.controllers;

import com.asdf.adminback.services.CertificateService;
import com.asdf.adminback.services.KeyStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import static com.asdf.adminback.util.Constants.*;

@RestController
@RequestMapping("api/certificate")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private KeyStoreService keyStoreService;

    @GetMapping(value="/getAliases")
    public ResponseEntity<List<String>> getAliases() {
        try {
            return new ResponseEntity<>(keyStoreService.getAliases(), HttpStatus.OK);
        } catch (KeyStoreException | NoSuchProviderException | IOException | CertificateException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/getCertificate")
    public ResponseEntity<X509Certificate> getCertificate(@RequestBody String alias) {
        alias = alias.substring(0, alias.length() - 1);
        System.out.println("Kljuc je " + alias);
        return new ResponseEntity<>((X509Certificate)keyStoreService.readCertificate(FILE_PATH, PWD, alias), HttpStatus.OK);
    }
}
