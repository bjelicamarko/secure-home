package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.AlarmNotificationDTO;
import com.asdf.myhomeback.dto.AppUserDTO;
import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.models.AlarmNotification;
import com.asdf.myhomeback.models.AppUser;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping ()
    @PreAuthorize("hasAuthority('GET_NOTIFICATIONS')")
    public ResponseEntity<List<AlarmNotificationDTO>> findAllForUser(Pageable pageable, HttpServletRequest request){
        String username = tokenUtils.getUsernameFromRequest(request);
        try{
            Page<AlarmNotification> anp = alarmNotificationService.findAllByUsername(username, pageable);
            HttpHeaders headers = ControllerUtils.createPageHeaderAttributes(anp);
            return new ResponseEntity<>(anp.stream().map(AlarmNotificationDTO::new).toList(), headers, HttpStatus.OK);
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

}