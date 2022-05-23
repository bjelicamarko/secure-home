package com.asdf.myhomeback.services;

import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.exceptions.RealEstateException;
import com.asdf.myhomeback.dto.RealEstateDTO;
import com.asdf.myhomeback.models.RealEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RealEstateService {

    RealEstate getRealEstateById(Long id);

    void saveRealEstate(RealEstateDTO realEstateDTO) throws RealEstateException;

    List<RealEstate> getRealEstateForUserToAssign(String username) throws AppUserException;

    Page<RealEstate> getRealEstatesOfUser(String username, Pageable pageable);
}
