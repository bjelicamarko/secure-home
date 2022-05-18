package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.RealEstateDTO;
import com.asdf.myhomeback.dto.UserRealEstateDTO;
import com.asdf.myhomeback.services.UserRealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ownerships")
public class UserRealEstateController {

    @Autowired
    private UserRealEstateService userRealEstateService;

    @PostMapping
    @PreAuthorize("hasAuthority('SAVE_USER_REAL_ESTATE')")
    public ResponseEntity<String> saveUserRealEstate(@RequestBody UserRealEstateDTO realEstateDTO){
        try{
            userRealEstateService.saveUserRealEstate(realEstateDTO);
            return new ResponseEntity<>("New ownership successfully added", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/changeRole")
    @PreAuthorize("hasAuthority('CHANGE_ROLE_USER_REAL_ESTATE')")
    public ResponseEntity<String> changeRoleInUserRealEstate(@RequestBody UserRealEstateDTO realEstateDTO){
        try{
            userRealEstateService.changeRoleInUserRealEstate(realEstateDTO);
            return new ResponseEntity<>("Role successfully changed", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
