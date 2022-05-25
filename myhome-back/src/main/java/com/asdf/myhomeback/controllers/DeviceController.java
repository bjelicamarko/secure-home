package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.DeviceDTO;
import com.asdf.myhomeback.models.Device;
import com.asdf.myhomeback.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    @PreAuthorize("hasAuthority('GET_DEVICES')")
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        try {
            List<Device> devices = deviceService.findAll();
            List<DeviceDTO> deviceDTOS = new ArrayList<>();
            devices.forEach(device -> deviceDTOS.add(new DeviceDTO(device)));
            return new ResponseEntity<>(deviceDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/names")
    @PreAuthorize("hasAuthority('GET_DEVICES')")
    public ResponseEntity<List<String>> getAllDeviceNames() {
        try {
            List<Device> devices = deviceService.findAll();
            List<String> names = new ArrayList<>();
            devices.forEach(device -> names.add(device.getName()));
            return new ResponseEntity<>(names, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
