package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.AlarmNotificationDTO;
import com.asdf.myhomeback.dto.AlarmNotificationWithIdDTO;
import com.asdf.myhomeback.dto.IdDTO;
import com.asdf.myhomeback.exceptions.AlarmNotificationException;
import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.models.AlarmNotification;
import com.asdf.myhomeback.security.TokenUtils;
import com.asdf.myhomeback.services.AlarmNotificationService;
import com.asdf.myhomeback.services.LogService;
import com.asdf.myhomeback.utils.ControllerUtils;
import com.asdf.myhomeback.utils.LogMessGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "api/alarmNotifications")
public class AlarmNotificationController {

    @Autowired
    private AlarmNotificationService alarmNotificationService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private LogService logService;

    @GetMapping
    @PreAuthorize("hasAuthority('GET_NOTIFICATIONS')")
    public ResponseEntity<AlarmNotificationDTO[]> findAllForUser(Pageable pageable, HttpServletRequest request){
        String username = tokenUtils.getUsernameFromRequest(request);
        try{
            Page<AlarmNotification> anp = alarmNotificationService.findAllByUsername(username, pageable);
            alarmNotificationService.saveAllAndSetSeen(username, anp.getContent(), logService);
            HttpHeaders headers = ControllerUtils.createPageHeaderAttributes(anp);
            return new ResponseEntity<>(anp.stream().map(AlarmNotificationDTO::new).toArray(AlarmNotificationDTO[]::new), headers, HttpStatus.OK);
        } catch (AppUserException e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/countNotSeen")
    @PreAuthorize("hasAuthority('GET_NOTIFICATIONS')")
    public ResponseEntity<Integer> countNotSeenForUser(HttpServletRequest request) {
        String username = tokenUtils.getUsernameFromRequest(request);
        try{
            Integer count = alarmNotificationService.countNotSeenForUsername(username);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (AppUserException e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/notSeen")
    @PreAuthorize("hasAuthority('GET_NOTIFICATIONS')")
    public ResponseEntity<AlarmNotificationWithIdDTO[]> getNotSeenForUser(HttpServletRequest request, Pageable pageable) {
        String username = tokenUtils.getUsernameFromRequest(request);
        try{
            Page<AlarmNotification> anp = alarmNotificationService.findAllByUsernameNotSeen(username, pageable);
            HttpHeaders headers = ControllerUtils.createPageHeaderAttributes(anp);
            return new ResponseEntity<>(anp.stream().map(AlarmNotificationWithIdDTO::new).toArray(AlarmNotificationWithIdDTO[]::new), headers, HttpStatus.OK);
        } catch (AppUserException e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/setSeen")
    @PreAuthorize("hasAuthority('GET_NOTIFICATIONS')")
    public ResponseEntity<String> setSeen(HttpServletRequest request, @RequestBody IdDTO id) {
        String username = tokenUtils.getUsernameFromRequest(request);
        try {
            alarmNotificationService.setSeen(username, id.getId());
            logService.generateInfoLog(LogMessGen.successfulUserNotificationSeen(username, id.getId()));
            return new ResponseEntity<>("Notification successfully marked as seen.", HttpStatus.OK);
        } catch (AppUserException | AlarmNotificationException e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>("Unexpected error occurred while setting seen notification", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
