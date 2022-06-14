package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.dto.RealEstateWithDevicesDTO;
import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.exceptions.RealEstateException;
import com.asdf.myhomeback.models.Device;
import com.asdf.myhomeback.models.RealEstate;
import com.asdf.myhomeback.repositories.RealEstateRepository;
import com.asdf.myhomeback.services.AppUserService;
import com.asdf.myhomeback.services.DeviceService;
import com.asdf.myhomeback.services.RealEstateService;
import com.asdf.myhomeback.utils.RealEstateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class RealEstateServiceImpl implements RealEstateService {

    @Autowired
    private RealEstateRepository realEstateRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private DeviceService deviceService;

    @Override
    public RealEstate getRealEstateById(Long id) {
        return realEstateRepository.findById(id).orElse(null);
    }

    @Override
    public void saveRealEstate(RealEstateWithDevicesDTO realEstateDTO) throws RealEstateException {
        RealEstate realEstate = new RealEstate(realEstateDTO);
        if(realEstateRepository.findByName(realEstate.getName()) != null)
            throw new RealEstateException(String.format("Real estate name '%s' is taken, try another one.", realEstate.getName()));
        RealEstateUtils.checkBasicRealEstateInfo(realEstate);

        Set<Device> devices = deviceService.findAllByNameInList(realEstateDTO.getDevices());
        realEstate.setDevices(devices);

        realEstateRepository.save(realEstate);
    }

    @Override
    public void updateRealEstate(RealEstateWithDevicesDTO realEstateDTO) throws RealEstateException {
        RealEstate realEstate = realEstateRepository.findByName(realEstateDTO.getName());
        if(realEstate == null)
            throw new RealEstateException(String.format("Real estate with name '%s' does not exist in database.", realEstateDTO.getName()));

        Set<Device> devices = deviceService.findAllByNameInList(realEstateDTO.getDevices());
        realEstate.setDevices(devices);

        realEstateRepository.save(realEstate);
    }


    @Override
    public List<RealEstate> getRealEstateForUserToAssign(String username) throws AppUserException {
        if(appUserService.findByUsernameVerifiedUnlocked(username) == null){
            throw new AppUserException(String.format("User with username '%s' doesnt exist.", username));
        }
        return realEstateRepository.getRealEstateForUserToAssign(username);
    }

    @Override
    public Page<RealEstate> getRealEstatesOfUser(String username, Pageable pageable) {
        return realEstateRepository.getRealEstatesOfUser(username, pageable);
    }

    @Override
    public Page<RealEstate> findAll(Pageable pageable) {
        return realEstateRepository.findAll(pageable);
    }

    @Override
    public List<Device> findDevicesByRealEstateName(String name) throws RealEstateException {
        if(realEstateRepository.findByName(name) == null)
            throw new RealEstateException(String.format("Real estate with name '%s' does not exist.", name));

        return realEstateRepository.findDevicesByRealEstateName(name);
    }

    @Override
    public List<RealEstate> getRealEstatesByDeviceName(String deviceName) {
        return realEstateRepository.getRealEstatesByDeviceName(deviceName);
    }

    @Override
    public RealEstate findRealEstateByName(String name) {
        return realEstateRepository.findRealEstateByName(name);
    }

    @Override
    public int findLowestReadPeriod(String name) {
        RealEstate re = realEstateRepository.findRealEstateByName(name);
        int min = Integer.MAX_VALUE;
        for (Device d : re.getDevices())
            if (d.getReadPeriod() <= min)
                min = d.getReadPeriod();
        return min;
    }
}
