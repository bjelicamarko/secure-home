package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.RealEstateDTO;
import com.asdf.myhomeback.dto.RealEstateToAssignDTO;
import com.asdf.myhomeback.models.RealEstate;
import com.asdf.myhomeback.services.RealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/realEstates")
public class RealEstateController {

    @Autowired
    private RealEstateService realEstateService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GET_REAL_ESTATE_BY_ID')")
    public ResponseEntity<RealEstateDTO> findRealEstate(@PathVariable(value = "id") Long id){
        try{
            RealEstate realEstate = realEstateService.getRealEstateById(id);
            return new ResponseEntity<>(new RealEstateDTO(realEstate), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RealEstateDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SAVE_REAL_ESTATE')")
    public ResponseEntity<String> saveRealEstate(@RequestBody RealEstateDTO realEstateDTO){
        try{
            realEstateService.saveRealEstate(realEstateDTO);
            return new ResponseEntity<>("Real estate successfully created", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Bad saving - name must be unique", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/toAssign")
    @PreAuthorize("hasAuthority('GET_USER_REAL_ESTATE_TO_ASSIGN')")
    public ResponseEntity<List<RealEstateToAssignDTO>> getRealEstateForUserToAssign(@RequestParam("username") String username){
        try{
            List<RealEstate> realEstates = realEstateService.getRealEstateForUserToAssign(username);
            List<RealEstateToAssignDTO> realEstateDTOS = new ArrayList<>();

            realEstates.forEach(realEstate -> realEstateDTOS.add(new RealEstateToAssignDTO(realEstate)));
            return new ResponseEntity<>(realEstateDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
