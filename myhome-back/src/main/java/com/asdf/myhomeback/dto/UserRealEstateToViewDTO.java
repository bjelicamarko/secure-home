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
public class UserRealEstateToViewDTO extends UserRealEstateDTO {
    private String realEstateName;

    public UserRealEstateToViewDTO(UserRealEstate userRealEstate) {
        super(userRealEstate);
        this.realEstateName = userRealEstate.getRealEstate().getName();
    }

}
