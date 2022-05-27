package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.DeviceDTO;
import com.asdf.myhomeback.dto.DeviceMessageDTO;
import com.asdf.myhomeback.exceptions.DeviceException;
import com.asdf.myhomeback.models.Device;
import com.asdf.myhomeback.models.DeviceMessage;
import com.asdf.myhomeback.services.DeviceMessageService;
import com.asdf.myhomeback.services.DeviceService;
import com.asdf.myhomeback.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceMessageService deviceMessageService;

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

    @GetMapping("/getAllMessagesFromDevice/{deviceName}")
    @PreAuthorize("hasAuthority('GET_ALL_MESSAGES_FROM_DEVICE')")
    public ResponseEntity<List<DeviceMessageDTO>> getAllMessagesFromDevice(@PathVariable String deviceName, Pageable pageable) {
        Page<DeviceMessage> deviceMessages = deviceMessageService.getAllMessagesFromDevice(deviceName, pageable);
        return new ResponseEntity<>(deviceMessages.stream().map(DeviceMessageDTO::new).toList(),
                ControllerUtils.createPageHeaderAttributes(deviceMessages), HttpStatus.OK);
    }

    @GetMapping("/filterMessages")
    @PreAuthorize("hasAuthority('FILTER_ALL_MESSAGES_FROM_DEVICE')")
    public ResponseEntity<List<DeviceMessageDTO>> filterMessages(
            @RequestParam(value = "deviceName", required = false) String deviceName,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "selectedStatus", required = false) String selectedStatus,
            Pageable pageable
    ) {
        try {
            Page<DeviceMessage> deviceMessages = deviceMessageService.filterMessages(deviceName, startDate, endDate, selectedStatus,
                    pageable);
            if (deviceMessages.getContent().size() == 0)
                return  new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(deviceMessages.stream().map(DeviceMessageDTO::new).toList(),
                    ControllerUtils.createPageHeaderAttributes(deviceMessages), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }
}