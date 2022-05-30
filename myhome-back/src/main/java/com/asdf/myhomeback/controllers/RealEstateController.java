package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.RealEstateWithDevicesDTO;
import com.asdf.myhomeback.dto.RealEstateWithPhotoAndRoleDTO;
import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.exceptions.RealEstateException;
import com.asdf.myhomeback.dto.RealEstateDTO;
import com.asdf.myhomeback.dto.RealEstateToAssignDTO;
import com.asdf.myhomeback.models.Device;
import com.asdf.myhomeback.models.RealEstate;
import com.asdf.myhomeback.security.TokenUtils;
import com.asdf.myhomeback.services.LogService;
import com.asdf.myhomeback.services.RealEstateService;
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
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/realEstates")
public class RealEstateController {

    @Autowired
    private RealEstateService realEstateService;

    @Autowired
    private UserRealEstateService userRealEstateService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private LogService logService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GET_REAL_ESTATE_BY_ID')")
    public ResponseEntity<RealEstateDTO> findRealEstate(@PathVariable(value = "id") Long id, HttpServletRequest req){
        try{
            RealEstate realEstate = realEstateService.getRealEstateById(id);
            return new ResponseEntity<>(new RealEstateDTO(realEstate), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String username = tokenUtils.getUsernameFromRequest(req);
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(new RealEstateDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SAVE_REAL_ESTATE')")
    public ResponseEntity<String> saveRealEstate(@RequestBody RealEstateWithDevicesDTO realEstateDTO, HttpServletRequest req){
        String username = tokenUtils.getUsernameFromRequest(req);
        try{
            realEstateService.saveRealEstate(realEstateDTO);
            logService.generateInfoLog(LogMessGen.successfulRealEstateCreation(username, realEstateDTO.getName()));
            return new ResponseEntity<>("Real estate successfully created", HttpStatus.OK);
        } catch (RealEstateException e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>("Unexpected error happened while saving real estate.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('SAVE_REAL_ESTATE')")
    public ResponseEntity<String> updateRealEstate(@RequestBody RealEstateWithDevicesDTO realEstateDTO, HttpServletRequest req){
        String username = tokenUtils.getUsernameFromRequest(req);
        try{
            realEstateService.updateRealEstate(realEstateDTO);
            logService.generateInfoLog(LogMessGen.successfulRealEstateUpdate(username, realEstateDTO.getName()));
            return new ResponseEntity<>("Real estate successfully updated", HttpStatus.OK);
        } catch (RealEstateException e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>("Unexpected error happened while updating real estate.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/toAssign")
    @PreAuthorize("hasAuthority('GET_USER_REAL_ESTATE_TO_ASSIGN')")
    public ResponseEntity<List<RealEstateToAssignDTO>> getRealEstateForUserToAssign(@RequestParam("username") String username, HttpServletRequest req){
        try{
            List<RealEstate> realEstates = realEstateService.getRealEstateForUserToAssign(username);
            List<RealEstateToAssignDTO> realEstateDTOS = new ArrayList<>();
            realEstates.forEach(realEstate -> realEstateDTOS.add(new RealEstateToAssignDTO(realEstate)));
            return new ResponseEntity<>(realEstateDTOS, HttpStatus.OK);
        } catch (AppUserException e) {
            e.printStackTrace();
            String userWhoDoes = tokenUtils.getUsernameFromRequest(req);
            logService.generateErrLog(LogMessGen.exMessUser(userWhoDoes, e.getMessage()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            String userWhoDoes = tokenUtils.getUsernameFromRequest(req);
            logService.generateErrLog(LogMessGen.internalServerError(userWhoDoes), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasAuthority('GET_USER_REAL_ESTATES')")
    public ResponseEntity<List<RealEstateWithPhotoAndRoleDTO>> getRealEstatesOfUser(HttpServletRequest request, Pageable pageable){
        try{
            String authToken = tokenUtils.getToken(request);
            String username = tokenUtils.getUsernameFromToken(authToken);
            Page<RealEstate> realEstates = realEstateService.getRealEstatesOfUser(username, pageable);
            List<String> roles = userRealEstateService.findUsersRoleInRealEstates(username, realEstates);

            List<RealEstateWithPhotoAndRoleDTO> list = new ArrayList<>();
            List<RealEstate> realEstates1 = realEstates.stream().toList();
            for (int i = 0; i < realEstates1.size(); i++) {
                list.add(new RealEstateWithPhotoAndRoleDTO(realEstates1.get(i), roles.get(i)));
            }
            return new ResponseEntity<>(list, ControllerUtils.createPageHeaderAttributes(realEstates), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String username = tokenUtils.getUsernameFromRequest(request);
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('GET_REAL_ESTATES')")
    public ResponseEntity<List<RealEstateWithPhotoAndRoleDTO>> getAllRealEstates(Pageable pageable, HttpServletRequest req) {
        try {
            Page<RealEstate> realEstates = realEstateService.findAll(pageable);
            List<RealEstateWithPhotoAndRoleDTO> realEstateDTOS = new ArrayList<>();
            realEstates.forEach((realEstate -> realEstateDTOS.add(new RealEstateWithPhotoAndRoleDTO(realEstate))));
            return new ResponseEntity<>(realEstateDTOS, ControllerUtils.createPageHeaderAttributes(realEstates), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String username = tokenUtils.getUsernameFromRequest(req);
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/deviceNames/{name}")
    @PreAuthorize("hasAuthority('GET_REAL_ESTATE_DEVICES')")
    public ResponseEntity<List<String>> findDevicesByRealEstateName(@PathVariable(value = "name") String name, HttpServletRequest req) {
        try {
            List<Device> devices = realEstateService.findDevicesByRealEstateName(name);
            List<String> deviceNames = new ArrayList<>();
            devices.forEach(device -> deviceNames.add(device.getName()));
            return new ResponseEntity<>(deviceNames, HttpStatus.OK);
        } catch (RealEstateException e) {
            e.printStackTrace();
            String username = tokenUtils.getUsernameFromRequest(req);
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            String username = tokenUtils.getUsernameFromRequest(req);
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
