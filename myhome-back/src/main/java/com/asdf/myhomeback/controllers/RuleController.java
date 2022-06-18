package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.RuleDTO;
import com.asdf.myhomeback.exceptions.RuleException;
import com.asdf.myhomeback.models.Rule;
import com.asdf.myhomeback.security.TokenUtils;
import com.asdf.myhomeback.services.LogService;
import com.asdf.myhomeback.services.RuleService;
import com.asdf.myhomeback.utils.LogMessGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "api/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @Autowired
    private LogService logService;

    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping
    @PreAuthorize("hasAuthority('GET_RULES')")
    public ResponseEntity<RuleDTO[]> getAllExistingRules(HttpServletRequest req){
        try {
            List<Rule> rules = ruleService.findAll();
            return new ResponseEntity<>(rules.stream().map(RuleDTO::new).toArray(RuleDTO[]::new), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String username = tokenUtils.getUsernameFromRequest(req);
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_RULE')")
    public ResponseEntity<String> createRule(@RequestBody RuleDTO ruleDTO, HttpServletRequest req){
        try {
            ruleService.save(ruleDTO);
            return new ResponseEntity<>("Rule successfully added", HttpStatus.OK);
        } catch (RuleException e) {
            String username = tokenUtils.getUsernameFromRequest(req);
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            String username = tokenUtils.getUsernameFromRequest(req);
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('DELETE_RULE')")
    public ResponseEntity<String> delete(@PathVariable Long id, HttpServletRequest req){
        try {
            ruleService.delete(id);
            return new ResponseEntity<>("Rule successfully deleted", HttpStatus.OK);
        } catch (RuleException e) {
            String username = tokenUtils.getUsernameFromRequest(req);
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            String username = tokenUtils.getUsernameFromRequest(req);
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
