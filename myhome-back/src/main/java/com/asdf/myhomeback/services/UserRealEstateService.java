package com.asdf.myhomeback.services;

import com.asdf.myhomeback.dto.UserRealEstateDTO;

public interface UserRealEstateService {

    void saveUserRealEstate(UserRealEstateDTO userRealEstateDTO) throws Exception;

    void changeRoleInUserRealEstate(UserRealEstateDTO realEstateDTO);
}
