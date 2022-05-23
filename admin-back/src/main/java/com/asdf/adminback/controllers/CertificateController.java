package com.asdf.adminback.controllers;

import com.asdf.adminback.dto.CertificateDTO;
import com.asdf.adminback.dto.RevokedCertificateDTO;
import com.asdf.adminback.exceptions.CertificateNotFound;
import com.asdf.adminback.services.CertificateService;
import com.asdf.adminback.services.KeyStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.*;
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
    // @PreAuthorize("hasAuthority('READ_KEYSTORE')")
    public ResponseEntity<List<String>> getAliases() {
        try {
            return new ResponseEntity<>(keyStoreService.getAliases(), HttpStatus.OK);
        } catch (KeyStoreException | NoSuchProviderException | IOException | CertificateException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/getCertificate/{alias}")
    // @PreAuthorize("hasAuthority('READ_CERTIFICATE')")
    public ResponseEntity<List<CertificateDTO>> getCertificate(@PathVariable String alias) {
        return new ResponseEntity<>(keyStoreService.readCertificateChain(FILE_PATH, PWD, alias)
                , HttpStatus.OK);
    }

    @PostMapping
    // @PreAuthorize("hasAuthority('SAVE_CERTIFICATE')")
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

    @PostMapping("/revoke")
    // @PreAuthorize("hasAuthority('REVOKE_CERTIFICATE')")
    public ResponseEntity<String> revokeCertificate(@RequestBody RevokedCertificateDTO revokedCertificate) {
        try {
            certificateService.revokeCertificate(revokedCertificate.getAlias(), revokedCertificate.getReason());
            return new ResponseEntity<>(String.format("Successfully revoked certificate (alias: %s)!", revokedCertificate.getAlias()), HttpStatus.OK);
        }
        catch (CertificateNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping("/validate/{alias}")
    // @PreAuthorize("hasAuthority('VALIDATE_CERTIFICATE')")
    public ResponseEntity<String> validateCertificate(@PathVariable String alias) {
        try {
            certificateService.validateCertificate(alias);
            return new ResponseEntity<>(String.format("Certificate (alias: %s) is valid!", alias), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
