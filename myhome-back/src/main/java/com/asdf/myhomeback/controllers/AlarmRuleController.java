package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.AlarmRuleDTO;
import com.asdf.myhomeback.exceptions.AlarmRuleException;
import com.asdf.myhomeback.models.AlarmRule;
import com.asdf.myhomeback.services.AlarmRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/alarmRules")
public class AlarmRuleController {

    @Autowired
    private AlarmRuleService alarmRuleService;

    @GetMapping
    @PreAuthorize("hasAuthority('GET_ALARM_RULES')")
    public ResponseEntity<List<AlarmRuleDTO>> getAllExistingAlarmRules(){
        try {
            List<AlarmRule> alarmRules = alarmRuleService.findAll();
            return new ResponseEntity<>(alarmRules.stream().map(AlarmRuleDTO::new).toList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALARM_RULE')")
    public ResponseEntity<String> createAlarmRule(@RequestBody AlarmRuleDTO alarmRuleDTO){
        try {
            alarmRuleService.save(alarmRuleDTO);
            return new ResponseEntity<>("Rule successfully added", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('DELETE_ALARM_RULE')")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
            alarmRuleService.delete(id);
            return new ResponseEntity<>("Rule successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
