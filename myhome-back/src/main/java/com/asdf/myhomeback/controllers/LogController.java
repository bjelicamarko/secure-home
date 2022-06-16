package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.DeviceMessageDTO;
import com.asdf.myhomeback.dto.LogDTO;
import com.asdf.myhomeback.models.DeviceMessage;
import com.asdf.myhomeback.models.Log;
import com.asdf.myhomeback.services.LogService;
import com.asdf.myhomeback.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_LOGS')")
    public ResponseEntity<LogDTO[]> getAllUsersButAdmin(Pageable pageable) {
        Page<Log> logs = logService.getAllLogs(pageable);

        return new ResponseEntity<>(logs.stream().map(LogDTO::new).toArray(LogDTO[]::new),
                ControllerUtils.createPageHeaderAttributes(logs), HttpStatus.OK);
    }

    @GetMapping("/filterLogs")
    @PreAuthorize("hasAuthority('FILTER_ALL_LOGS')")
    public ResponseEntity<LogDTO[]> filterMessages(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "selectedLevel", required = false) String selectedLevel,
            @RequestParam(value = "searchValue", required = false) String searchValue,
            @RequestParam(value = "messageRegex", required = false) String messageRegex,
            Pageable pageable
    ) {
        try {
            Page<Log> deviceMessages = logService.filterLogs(startDate, endDate, selectedLevel, searchValue, messageRegex, pageable);
            if (deviceMessages.getContent().size() == 0)
                return  new ResponseEntity<>(new LogDTO[]{}, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(deviceMessages.stream().map(LogDTO::new).toArray(LogDTO[]::new),
                    ControllerUtils.createPageHeaderAttributes(deviceMessages), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new LogDTO[]{}, HttpStatus.BAD_REQUEST);
        }
    }
}
