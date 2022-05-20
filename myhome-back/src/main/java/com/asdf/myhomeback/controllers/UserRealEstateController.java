package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.RealEstateDTO;
import com.asdf.myhomeback.dto.UserRealEstateDTO;
import com.asdf.myhomeback.dto.UserRealEstateToViewDTO;
import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.exceptions.RealEstateException;
import com.asdf.myhomeback.exceptions.UserRealEstateException;
import com.asdf.myhomeback.models.UserRealEstate;
import com.asdf.myhomeback.services.UserRealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @PutMapping
    @PreAuthorize("hasAuthority('CHANGE_ROLE_USER_REAL_ESTATE')")
    public ResponseEntity<String> changeRoleInUserRealEstate(@RequestBody UserRealEstateDTO realEstateDTO){
        try{
            userRealEstateService.changeRoleInUserRealEstate(realEstateDTO);
            return new ResponseEntity<>("Role successfully changed", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/fromUser")
    @PreAuthorize("hasAuthority('GET_USER_REAL_ESTATES')")
    public ResponseEntity<List<UserRealEstateToViewDTO>> getUserRealEstatesFromUser(@RequestParam("username") String username){
        try{
            List<UserRealEstate> userRealEstates = userRealEstateService.getUserRealEstatesFromUser(username);
            List<UserRealEstateToViewDTO> userRealEstateToViewDTOS = new ArrayList<>();
            userRealEstates.forEach(userRealEstate -> userRealEstateToViewDTOS.add(new UserRealEstateToViewDTO(userRealEstate)));
            return new ResponseEntity<>(userRealEstateToViewDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/delete")
    @PreAuthorize("hasAuthority('DELETE_USER_REAL_ESTATE')")
    public ResponseEntity<String> deleteUserRealEstate(@RequestBody UserRealEstateDTO userRealEstateDTO){
        try{
            userRealEstateService.deleteUserRealEstate(userRealEstateDTO);
            return new ResponseEntity<>("User real estate successfully deleted", HttpStatus.OK);
        } catch (AppUserException | RealEstateException | UserRealEstateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
