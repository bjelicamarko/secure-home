package com.asdf.adminback.controllers;

import com.asdf.adminback.exceptions.CSRException;
import com.asdf.adminback.models.CSR;
import com.asdf.adminback.services.CSRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/csrs")
public class CSRController {

    @Autowired
    private CSRService csrService;

    @GetMapping
    public ResponseEntity<List<CSR>> findAll() {
        return new ResponseEntity<>(csrService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value="/verified")
    public ResponseEntity<List<CSR>> findAllVerified() {
        return new ResponseEntity<>(csrService.findAllVerified(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CSR> findOneById(@PathVariable Long id) {
        CSR csr = csrService.findOneById(id);

        if(csr != null)
            return new ResponseEntity<>(csr, HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<String> save(@RequestBody CSR csr) {
        try {
            csrService.save(csr);
            return new ResponseEntity<>("Certificate signing request successfully created. Check your email to verify it.", HttpStatus.OK);
        }
        catch (CSRException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while creating csr.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/verify-csr/{id}")
    public ResponseEntity<String> verify(@PathVariable Long id) {
        try {
            csrService.verify(id);
            return new ResponseEntity<>("CSR successfully verified.", HttpStatus.OK);
        }
        catch (CSRException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Unknown error happened verifying csr.", HttpStatus.BAD_REQUEST);
        }
    }

}
