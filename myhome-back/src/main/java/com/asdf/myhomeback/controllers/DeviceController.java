package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.DeviceDTO;
import com.asdf.myhomeback.dto.DeviceMessageDTO;
import com.asdf.myhomeback.dto.ReportDTO;
import com.asdf.myhomeback.models.Device;
import com.asdf.myhomeback.models.DeviceMessage;
import com.asdf.myhomeback.security.TokenUtils;
import com.asdf.myhomeback.services.DeviceMessageService;
import com.asdf.myhomeback.services.DeviceService;
import com.asdf.myhomeback.services.LogService;
import com.asdf.myhomeback.services.UserRealEstateService;
import com.asdf.myhomeback.utils.ControllerUtils;
import com.asdf.myhomeback.utils.LogMessGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceMessageService deviceMessageService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private LogService logService;

    @Autowired
    private UserRealEstateService userRealEstateService;

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
        try {
            deviceMessageService.saveAll(deviceMessageDTOs);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("All good.", HttpStatus.OK);
    }

    @GetMapping("/getAllMessagesFromDevice/{deviceName}")
    @PreAuthorize("hasAuthority('GET_ALL_MESSAGES_FROM_DEVICE')")
    public ResponseEntity<DeviceMessageDTO[]> getAllMessagesFromDevice(@PathVariable String deviceName, Pageable pageable) {
        Page<DeviceMessage> deviceMessages = deviceMessageService.getAllMessagesFromDevice(deviceName, pageable);
        return new ResponseEntity<>(deviceMessages.stream().map(DeviceMessageDTO::new).toArray(DeviceMessageDTO[]::new),
                ControllerUtils.createPageHeaderAttributes(deviceMessages), HttpStatus.OK);
    }

    @GetMapping("/filterMessages")
    @PreAuthorize("hasAuthority('FILTER_ALL_MESSAGES_FROM_DEVICE')")
    public ResponseEntity<DeviceMessageDTO[]> filterMessages(
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
                return  new ResponseEntity<>(new DeviceMessageDTO[]{}, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(deviceMessages.stream().map(DeviceMessageDTO::new).toArray(DeviceMessageDTO[]::new),
                    ControllerUtils.createPageHeaderAttributes(deviceMessages), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new DeviceMessageDTO[]{}, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/createReport")
    @PreAuthorize("hasAuthority('CREATE_REPORT')")
    public ResponseEntity<String> createReport(
            @RequestParam(value = "deviceName", required = false) String deviceName,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "selectedStatus", required = false) String selectedStatus
    ) {
        try {
            List<DeviceMessage> deviceMessages = deviceMessageService.createReport(deviceName, startDate, endDate, selectedStatus);
            if (deviceMessages.size() == 0)
                return  new ResponseEntity<>("Empty list", HttpStatus.NOT_FOUND);
            ReportDTO report = new ReportDTO(deviceName, startDate, endDate, selectedStatus, deviceMessages);
            return new ResponseEntity<>(report.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllMessagesFromRealEstate/{realEstate}")
    @PreAuthorize("hasAuthority('GET_ALL_MESSAGES_FROM_REAL_ESTATE')")
    public ResponseEntity<DeviceMessageDTO[]> getAllMessagesFromRealEstate( HttpServletRequest request,
                                                                                @PathVariable String realEstate, Pageable pageable) {
        String authToken = tokenUtils.getToken(request);
        String username = tokenUtils.getUsernameFromToken(authToken);

        if (userRealEstateService.isUserInRealEstate(username, realEstate)){
            logService.generateErrLog(LogMessGen.userNotInRealEstate(username, realEstate));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<DeviceMessage> deviceMessages = deviceMessageService.findAllByDeviceNameInOrderByIdDesc(realEstate, pageable);
        return new ResponseEntity<>(deviceMessages.stream().map(DeviceMessageDTO::new).toArray(DeviceMessageDTO[]::new),
                ControllerUtils.createPageHeaderAttributes(deviceMessages), HttpStatus.OK);
    }

    @GetMapping("/filterAllMessages")
    @PreAuthorize("hasAuthority('FILTER_ALL_MESSAGES_FROM_ESTATE')")
    public ResponseEntity<DeviceMessageDTO[]> filterAllMesages( HttpServletRequest request,
            @RequestParam(value = "realEstateName", required = false) String realEstateName,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "selectedStatus", required = false) String selectedStatus,
            Pageable pageable
    ) {
        try {
            String authToken = tokenUtils.getToken(request);
            String username = tokenUtils.getUsernameFromToken(authToken);

            if (userRealEstateService.isUserInRealEstate(username, realEstateName)){
                logService.generateErrLog(LogMessGen.userNotInRealEstate(username, realEstateName));
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Page<DeviceMessage> deviceMessages = deviceMessageService.filterAllMessages(realEstateName, startDate, endDate, selectedStatus,
                    pageable);
            if (deviceMessages.getContent().size() == 0)
                return  new ResponseEntity<>(new DeviceMessageDTO[]{}, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(deviceMessages.stream().map(DeviceMessageDTO::new).toArray(DeviceMessageDTO[]::new),
                    ControllerUtils.createPageHeaderAttributes(deviceMessages), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new DeviceMessageDTO[]{}, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/createAllReport")
    @PreAuthorize("hasAuthority('CREATE_ALL_REPORT')")
    public ResponseEntity<String> createAllReport( HttpServletRequest request,
            @RequestParam(value = "realEstateName", required = false) String realEstateName,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "selectedStatus", required = false) String selectedStatus
    ) {
        try {

            String authToken = tokenUtils.getToken(request);
            String username = tokenUtils.getUsernameFromToken(authToken);

            if (userRealEstateService.isUserInRealEstate(username, realEstateName)){
                logService.generateErrLog(LogMessGen.userNotInRealEstate(username, realEstateName));
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            List<DeviceMessage> deviceMessages = deviceMessageService.createAllReport(realEstateName, startDate, endDate, selectedStatus);
            if (deviceMessages.size() == 0)
                return  new ResponseEntity<>("Empty list", HttpStatus.NOT_FOUND);
            ReportDTO report = new ReportDTO("all", startDate, endDate, selectedStatus, deviceMessages);
            return new ResponseEntity<>(report.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{name}")
    @PreAuthorize("hasAuthority('GET_DEVICE')")
    public ResponseEntity<DeviceDTO> getDeviceByName(@PathVariable String name) {
        try {
            Device device = deviceService.findOneByName(name);
            return new ResponseEntity<>(new DeviceDTO(device), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/updateDeviceReadPeriod")
    @PreAuthorize("hasAuthority('UPDATE_DEVICE')")
    public ResponseEntity<String> updateDeviceReadPeriod(@RequestBody Device d) {
        try {
            deviceService.updateDeviceReadPeriod(d);
            return new ResponseEntity<>("Device updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}