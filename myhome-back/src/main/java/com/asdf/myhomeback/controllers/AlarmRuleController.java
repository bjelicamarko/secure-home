package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.AlarmRuleDTO;
import com.asdf.myhomeback.exceptions.AlarmRuleException;
import com.asdf.myhomeback.services.AlarmRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/alarmRules")
public class AlarmRuleController {

    @Autowired
    private AlarmRuleService alarmRuleService;

    @PostMapping
    public ResponseEntity<String> createAlarmRule(@RequestBody AlarmRuleDTO alarmRuleDTO){
        try {
            alarmRuleService.save(alarmRuleDTO);
            return new ResponseEntity<>("Rule successfully added", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateAlarmRule(@RequestBody AlarmRuleDTO alarmRuleDTO){
        try {
            alarmRuleService.update(alarmRuleDTO);
            return new ResponseEntity<>("Rule successfully updated", HttpStatus.OK);
        }
        catch (AlarmRuleException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
