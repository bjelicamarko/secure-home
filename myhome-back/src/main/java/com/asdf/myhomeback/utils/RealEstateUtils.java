package com.asdf.myhomeback.utils;

import com.asdf.myhomeback.Exception.RealEstateException;
import com.asdf.myhomeback.models.RealEstate;

import static com.asdf.myhomeback.utils.BasicValidator.isValidRealEstateName;

public class RealEstateUtils {

    public static void checkBasicRealEstateInfo(RealEstate realEstate) throws RealEstateException {
        if(realEstate == null)
            throw new RealEstateException("Invalid real estate sent from front!");
        if(!isValidRealEstateName(realEstate.getName()))
            throw new RealEstateException("Real estate name should only contain letters and whitespaces with length [3-20].");
    }


}
