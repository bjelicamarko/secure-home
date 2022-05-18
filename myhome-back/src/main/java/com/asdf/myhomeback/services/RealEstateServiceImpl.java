package com.asdf.myhomeback.services;

import com.asdf.myhomeback.dto.RealEstateDTO;
import com.asdf.myhomeback.models.RealEstate;
import com.asdf.myhomeback.repositories.RealEstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RealEstateServiceImpl implements RealEstateService {

    @Autowired
    private RealEstateRepository realEstateRepository;

    @Override
    public RealEstate getRealEstateById(Long id) {
        return realEstateRepository.findById(id).orElse(null);
    }

    @Override
    public void saveRealEstate(RealEstateDTO realEstateDTO) {
        realEstateRepository.save(new RealEstate(realEstateDTO));
    }
}
