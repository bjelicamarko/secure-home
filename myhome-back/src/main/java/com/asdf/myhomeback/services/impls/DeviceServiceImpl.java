package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.models.Device;
import com.asdf.myhomeback.repositories.DeviceRepository;
import com.asdf.myhomeback.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Set<Device> findAllByNameInList(List<String> names) {
        return deviceRepository.findAllByNameInList(names);
    }

}
