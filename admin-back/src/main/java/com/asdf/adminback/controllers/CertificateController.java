package com.asdf.adminback.controllers;

import com.asdf.adminback.models.CertificateDTO;
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
import java.security.cert.CertificateException;
import java.util.List;
import static com.asdf.adminback.util.Constants.*;
import com.asdf.adminback.dto.CertificateSigningDTO;
import com.asdf.adminback.exceptions.CertificateSigningDTOException;

@RestController
@RequestMapping("api/certificates")
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
    public ResponseEntity<List<CertificateDTO>> getCertificate(@RequestBody String alias) {
        alias = alias.substring(0, alias.length() - 1);
        System.out.println("Kljuc je " + alias);
        return new ResponseEntity<>(keyStoreService.readCertificateChain(FILE_PATH, PWD, alias)
                , HttpStatus.OK);
    }

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
