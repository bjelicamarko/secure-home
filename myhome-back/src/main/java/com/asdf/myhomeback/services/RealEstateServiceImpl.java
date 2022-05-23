package com.asdf.myhomeback.services;

import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.exceptions.RealEstateException;
import com.asdf.myhomeback.dto.RealEstateDTO;
import com.asdf.myhomeback.models.RealEstate;
import com.asdf.myhomeback.repositories.RealEstateRepository;
import com.asdf.myhomeback.utils.RealEstateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RealEstateServiceImpl implements RealEstateService {

    @Autowired
    private RealEstateRepository realEstateRepository;

    @Autowired
    private AppUserService appUserService;

    @Override
    public RealEstate getRealEstateById(Long id) {
        return realEstateRepository.findById(id).orElse(null);
    }

    @Override
    public void saveRealEstate(RealEstateDTO realEstateDTO) throws RealEstateException {
        RealEstate realEstate = new RealEstate(realEstateDTO);

        if(realEstateRepository.findByName(realEstate.getName()) != null)
            throw new RealEstateException("Real estate name is taken, try another one.");
        RealEstateUtils.checkBasicRealEstateInfo(realEstate);

        realEstateRepository.save(new RealEstate(realEstateDTO));
    }

    @Override
    public List<RealEstate> getRealEstateForUserToAssign(String username) throws AppUserException {
        if(appUserService.findByUsernameVerifiedUnlocked(username) == null){
            throw new AppUserException("This user doesnt exist.");
        }
        return realEstateRepository.getRealEstateForUserToAssign(username);
    }

    @Override
    public Page<RealEstate> getRealEstatesOfUser(String username, Pageable pageable) {
        return realEstateRepository.getRealEstatesOfUser(username, pageable);
    }
}
