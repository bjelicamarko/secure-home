package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.*;
import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.exceptions.RealEstateException;
import com.asdf.myhomeback.exceptions.UserRealEstateException;
import com.asdf.myhomeback.models.Device;
import com.asdf.myhomeback.models.RealEstate;
import com.asdf.myhomeback.models.UserRealEstate;
import com.asdf.myhomeback.security.TokenUtils;
import com.asdf.myhomeback.services.DeviceService;
import com.asdf.myhomeback.services.LogService;
import com.asdf.myhomeback.services.RealEstateService;
import com.asdf.myhomeback.services.UserRealEstateService;
import com.asdf.myhomeback.utils.LogMessGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/ownerships")
public class UserRealEstateController {

    @Autowired
    private UserRealEstateService userRealEstateService;

    @Autowired
    private RealEstateService realEstateService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private LogService logService;

    @PostMapping
    @PreAuthorize("hasAuthority('SAVE_USER_REAL_ESTATE')")
    public ResponseEntity<String> saveUserRealEstate(@RequestBody UserRealEstateDTO realEstateDTO, HttpServletRequest req){
        String username = tokenUtils.getUsernameFromRequest(req);
        try{
            UserRealEstate ure = userRealEstateService.saveUserRealEstate(realEstateDTO);
            String realEstateName = ure.getRealEstate().getName();
            String logMess = LogMessGen.successfulUserRealEstateCreation(username, realEstateDTO.getUsername(), realEstateName, realEstateDTO.getRole());
            logService.generateInfoLog(logMess);
            return new ResponseEntity<>("New ownership successfully added", HttpStatus.OK);
        } catch (AppUserException | RealEstateException | UserRealEstateException e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CHANGE_ROLE_USER_REAL_ESTATE')")
    public ResponseEntity<String> changeRoleInUserRealEstate(@RequestBody UserRealEstateDTO realEstateDTO, HttpServletRequest req){
        String username = tokenUtils.getUsernameFromRequest(req);
        try{
            UserRealEstate ure = userRealEstateService.changeRoleInUserRealEstate(realEstateDTO);
            String realEstateName = ure.getRealEstate().getName();
            String logMess = LogMessGen.successfulUserRealEstateRoleChange(username, realEstateDTO.getUsername(), realEstateName, realEstateDTO.getRole());
            logService.generateInfoLog(logMess);
            return new ResponseEntity<>("Role successfully changed", HttpStatus.OK);
        } catch (AppUserException | RealEstateException | UserRealEstateException e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/fromUser")
    @PreAuthorize("hasAuthority('GET_USER_REAL_ESTATES')")
    public ResponseEntity<List<UserRealEstateToViewDTO>> getUserRealEstatesFromUser(@RequestParam("username") String username,
                                                                                    HttpServletRequest req){
        try{
            List<UserRealEstate> userRealEstates = userRealEstateService.getUserRealEstatesFromUser(username);
            List<UserRealEstateToViewDTO> userRealEstateToViewDTOS = new ArrayList<>();
            userRealEstates.forEach(userRealEstate -> userRealEstateToViewDTOS.add(new UserRealEstateToViewDTO(userRealEstate)));
            return new ResponseEntity<>(userRealEstateToViewDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String userWhoDoes = tokenUtils.getUsernameFromRequest(req);
            logService.generateErrLog(LogMessGen.internalServerError(userWhoDoes), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/delete")
    @PreAuthorize("hasAuthority('DELETE_USER_REAL_ESTATE')")
    public ResponseEntity<String> deleteUserRealEstate(@RequestBody UserRealEstateDTO userRealEstateDTO, HttpServletRequest req){
        String username = tokenUtils.getUsernameFromRequest(req);
        try{
            userRealEstateService.deleteUserRealEstate(userRealEstateDTO);
            RealEstate re = realEstateService.getRealEstateById(userRealEstateDTO.getRealEstateId());
            String realEstateName = re.getName();
            String logMess = LogMessGen.successfulUserRealEstateDelete(username, userRealEstateDTO.getUsername(), realEstateName);
            logService.generateInfoLog(logMess);
            return new ResponseEntity<>("User real estate successfully deleted", HttpStatus.OK);
        } catch (AppUserException | RealEstateException | UserRealEstateException e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{name}")
    @PreAuthorize("hasAuthority('GET_USER_REAL_ESTATE')")
    public ResponseEntity<RealEstateWithHouseholdAndDevicesDTO> getOwnerRealEstate(HttpServletRequest request, @PathVariable("name") String name){
        try {
            String authToken = tokenUtils.getToken(request);
            String username = tokenUtils.getUsernameFromToken(authToken);

            if (userRealEstateService.isUserInRealEstate(username, name)){
                logService.generateErrLog(LogMessGen.userNotInRealEstate(username, name));
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            List<String> household = userRealEstateService.getUsersFromByRealEstateName(username, name);
            List<Device> devices = realEstateService.findDevicesByRealEstateName(name);

            return new ResponseEntity<>(new RealEstateWithHouseholdAndDevicesDTO(household, devices),  HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String username = tokenUtils.getUsernameFromRequest(request);
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
