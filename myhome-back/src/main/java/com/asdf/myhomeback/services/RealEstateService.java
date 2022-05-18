package com.asdf.myhomeback.services;

import com.asdf.myhomeback.dto.RealEstateDTO;
import com.asdf.myhomeback.models.RealEstate;

public interface RealEstateService {

    RealEstate getRealEstateById(Long id);

    void saveRealEstate(RealEstateDTO realEstateDTO);
}