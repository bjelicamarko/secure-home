package com.asdf.myhomeback.dto;

import com.asdf.myhomeback.models.UserRealEstate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class UserRealEstateDTO {

    private String username;
    private Long realEstateId;
    private String role;

    public UserRealEstateDTO(UserRealEstate userRealEstate) {
        this.username = userRealEstate.getUser().getUsername();
        this.realEstateId = userRealEstate.getRealEstate().getId();
        this.role = userRealEstate.getRole().toString();
    }
}
