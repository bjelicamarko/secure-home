package com.asdf.myhomeback.services;

import com.asdf.myhomeback.dto.UserRealEstateDTO;
import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.exceptions.RealEstateException;
import com.asdf.myhomeback.exceptions.UserRealEstateException;
import com.asdf.myhomeback.models.UserRealEstate;
import com.asdf.myhomeback.models.enums.UserRoleEnum;

import java.util.List;

public interface UserRealEstateService {

    void saveUserRealEstate(UserRealEstateDTO userRealEstateDTO) throws Exception;

    void changeRoleInUserRealEstate(UserRealEstateDTO realEstateDTO) throws Exception;

    List<UserRealEstate> getUserRealEstatesFromUser(String username);

    int findCountOfRole(Long userId, UserRoleEnum role);

    void deleteUserRealEstate(UserRealEstateDTO userRealEstateDTO) throws AppUserException, RealEstateException, UserRealEstateException;
}
