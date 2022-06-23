package com.asdf.adminback.controllers;

import com.asdf.adminback.dto.CertificateDTO;
import com.asdf.adminback.dto.RevokedCertificateDTO;
import com.asdf.adminback.exceptions.CertificateNotFound;
import com.asdf.adminback.security.TokenUtils;
import com.asdf.adminback.services.CertificateService;
import com.asdf.adminback.services.KeyStoreService;
import com.asdf.adminback.services.LogService;
import com.asdf.adminback.util.LogMessGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import static com.asdf.adminback.util.Constants.*;
import com.asdf.adminback.dto.CertificateSigningDTO;
import com.asdf.adminback.exceptions.CertificateSigningDTOException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private KeyStoreService keyStoreService;

    @Autowired
    private LogService logService;

    @Autowired
    private TokenUtils tokenUtils;

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

    @GetMapping(value = "/getCertificate/{alias}")
    // @PreAuthorize("hasAuthority('READ_CERTIFICATE')")
    public ResponseEntity<List<CertificateDTO>> getCertificate(@PathVariable String alias) {
        return new ResponseEntity<>(keyStoreService.readCertificateChain(FILE_PATH, PWD, alias)
                , HttpStatus.OK);
    }

    @PostMapping
    // @PreAuthorize("hasAuthority('SAVE_CERTIFICATE')")
    public ResponseEntity<String> save(@RequestBody CertificateSigningDTO certificateSigningDTO, HttpServletRequest req) {
        String username = tokenUtils.getUsernameFromRequest(req);
        try {
            certificateService.createAndWriteLeafCertificate(certificateSigningDTO);
            logService.generateInfoLog(LogMessGen.saveCertMess(username, certificateSigningDTO.getCertificateDataDTO()));
            return new ResponseEntity<>("Certificate successfully created.", HttpStatus.OK);
        }
        catch (CertificateSigningDTOException e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>("Unknown exception happened while creating certificate", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/revoke")
    // @PreAuthorize("hasAuthority('REVOKE_CERTIFICATE')")
    public ResponseEntity<String> revokeCertificate(@RequestBody RevokedCertificateDTO revokedCertificate, HttpServletRequest req) {
        String username = tokenUtils.getUsernameFromRequest(req);
        try {
            certificateService.revokeCertificate(revokedCertificate.getAlias(), revokedCertificate.getReason());
            logService.generateInfoLog(LogMessGen.revokeCertMess(username, revokedCertificate));
            return new ResponseEntity<>(String.format("Successfully revoked certificate (alias: %s)!", revokedCertificate.getAlias()), HttpStatus.OK);
        } catch (CertificateNotFound e) {
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/validate")
    // @PreAuthorize("hasAuthority('VALIDATE_CERTIFICATE')")
    public ResponseEntity<String> validateCertificate(@RequestBody String alias, HttpServletRequest req) {
        String username = tokenUtils.getUsernameFromRequest(req);
        try {
            certificateService.validateCertificate(alias);
            logService.generateInfoLog(LogMessGen.certValidationSucc(username, alias));
            return new ResponseEntity<>(String.format("Certificate (alias: %s) is valid!", alias), HttpStatus.OK);
        }
        catch (Exception e) {
            logService.generateErrLog(LogMessGen.certValidationEx(username, alias, e.getMessage()), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
