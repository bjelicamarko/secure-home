package com.asdf.myhomeback.dto;

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
}
