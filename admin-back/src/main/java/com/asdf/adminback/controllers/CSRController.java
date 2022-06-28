package com.asdf.adminback.controllers;

import com.asdf.adminback.exceptions.CSRException;
import com.asdf.adminback.models.CSR;
import com.asdf.adminback.security.TokenUtils;
import com.asdf.adminback.services.CSRService;
import com.asdf.adminback.services.LogService;
import com.asdf.adminback.util.LogMessGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/csrs")
public class CSRController {

    @Autowired
    private CSRService csrService;

    @Autowired
    private LogService logService;

    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping
    public ResponseEntity<List<CSR>> findAll() {
        return new ResponseEntity<>(csrService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value="/verified")
    @PreAuthorize("hasAuthority('READ_VERIFIED_CSRS')")
    public ResponseEntity<List<CSR>> findAllVerified() {
        return new ResponseEntity<>(csrService.findAllVerified(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CSR> findOneById(@PathVariable Long id, HttpServletRequest req) {
        CSR csr = csrService.findOneById(id);

        if(csr != null)
            return new ResponseEntity<>(csr, HttpStatus.OK);

        String username = tokenUtils.getUsernameFromRequest(req);
        logService.generateInfoLog(LogMessGen.unSuccCsrGet(username, id));
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody CSR csr, HttpServletRequest req) {
        String username = tokenUtils.getUsernameFromRequest(req);
        try {
            csrService.save(csr);
            logService.generateInfoLog(LogMessGen.saveCsrMess(csr));
            return new ResponseEntity<>("Certificate signing request successfully created. Check your email to verify it.", HttpStatus.OK);
        }
        catch (CSRException e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.saveCsrErrMess(e.getMessage(), csr));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.saveCsrInternalErrMess(csr), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>("Unknown error happened while creating csr.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify-csr")
    public ResponseEntity<String> verify(@RequestBody String securityCode) {
        try {
            CSR csr = csrService.verify(securityCode);
            logService.generateInfoLog(LogMessGen.csrVerification(csr.getId(), securityCode));
            return new ResponseEntity<>("CSR successfully verified.", HttpStatus.OK);
        }
        catch (CSRException e) {
            logService.generateErrLog(LogMessGen.csrVerificationErr(e.getMessage(), securityCode));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.csrVerificationErr("Unknown error happened verifying csr.", securityCode),  Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>("Unknown error happened verifying csr.", HttpStatus.BAD_REQUEST);
        }
    }

}
