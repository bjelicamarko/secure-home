package com.asdf.myhomeback.services;

import com.asdf.myhomeback.dto.UserRealEstateDTO;
import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.exceptions.RealEstateException;
import com.asdf.myhomeback.exceptions.UserRealEstateException;
import com.asdf.myhomeback.models.RealEstate;
import com.asdf.myhomeback.models.UserRealEstate;
import com.asdf.myhomeback.models.enums.UserRoleEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserRealEstateService {

    UserRealEstate saveUserRealEstate(UserRealEstateDTO userRealEstateDTO) throws Exception;

    UserRealEstate changeRoleInUserRealEstate(UserRealEstateDTO realEstateDTO) throws Exception;

    List<UserRealEstate> getUserRealEstatesFromUser(String username);

    int findCountOfRole(Long userId, UserRoleEnum role);

    void deleteUserRealEstate(UserRealEstateDTO userRealEstateDTO) throws AppUserException, RealEstateException, UserRealEstateException;

    List<String> findUsersRoleInRealEstates(String username, Page<RealEstate> realEstates);

    List<String> getUsersFromByRealEstateName(String username, String name);

    boolean isUserInRealEstate(String username, String name);

    String findRoleInRealEstateByName(String username, String name);
}
