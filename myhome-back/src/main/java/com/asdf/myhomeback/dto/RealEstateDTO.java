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
public class RealEstateDTO {
    private String name;

    public RealEstateDTO(RealEstate realEstate) {
        this.name = realEstate.getName();
    }
}
