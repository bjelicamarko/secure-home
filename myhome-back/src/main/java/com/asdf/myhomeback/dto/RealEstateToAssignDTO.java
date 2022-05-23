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
public class RealEstateToAssignDTO extends RealEstateDTO {
    private Long id;

    public RealEstateToAssignDTO(RealEstate realEstate) {
        super(realEstate);
        this.id = realEstate.getId();
    }
}
