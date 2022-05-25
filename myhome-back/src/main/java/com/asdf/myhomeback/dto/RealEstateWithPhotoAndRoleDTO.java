package com.asdf.myhomeback.dto;

import com.asdf.myhomeback.models.RealEstate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RealEstateWithPhotoAndRoleDTO extends RealEstateDTO {

    private String photo;
    private String role; // role of user in that real estate

    public RealEstateWithPhotoAndRoleDTO(RealEstate realEstate, String role) {
        super(realEstate);
        this.photo = realEstate.getPhoto();
        this.role = role;
    }

    public RealEstateWithPhotoAndRoleDTO(RealEstate realEstate) {
        super(realEstate);
        this.photo = realEstate.getPhoto();
        this.role = null;
    }
}
