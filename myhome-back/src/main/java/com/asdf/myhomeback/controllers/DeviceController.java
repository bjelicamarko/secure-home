package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.DeviceMessageDTO;
import com.asdf.myhomeback.models.DeviceMessage;
import com.asdf.myhomeback.services.DeviceMessageService;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/devices")
public class DeviceController {

    @Autowired
    private DeviceMessageService deviceMessageService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveDeviceMessage(@RequestBody DeviceMessageDTO deviceMessageDTO) {
        deviceMessageService.save(new DeviceMessage(deviceMessageDTO));
        return new ResponseEntity<>("All good.", HttpStatus.OK);
    }

    @PostMapping(value = "/all", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveAllDeviceMessages(@RequestBody List<DeviceMessageDTO> deviceMessageDTOs) {
        deviceMessageService.saveAll(deviceMessageDTOs.stream().map(DeviceMessage::new).toList());
        return new ResponseEntity<>("All good.", HttpStatus.OK);
    }
}
