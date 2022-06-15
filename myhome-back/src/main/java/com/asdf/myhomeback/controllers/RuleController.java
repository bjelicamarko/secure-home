package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.RuleDTO;
import com.asdf.myhomeback.models.Rule;
import com.asdf.myhomeback.services.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @GetMapping
    @PreAuthorize("hasAuthority('GET_RULES')")
    public ResponseEntity<List<RuleDTO>> getAllExistingRules(){
        try {
            List<Rule> rules = ruleService.findAll();
            return new ResponseEntity<>(rules.stream().map(RuleDTO::new).toList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_RULE')")
    public ResponseEntity<String> createRule(@RequestBody RuleDTO ruleDTO){
        try {
            ruleService.save(ruleDTO);
            return new ResponseEntity<>("Rule successfully added", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('DELETE_RULE')")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
            ruleService.delete(id);
            return new ResponseEntity<>("Rule successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
