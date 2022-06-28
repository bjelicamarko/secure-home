package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.exceptions.DeviceException;
import com.asdf.myhomeback.models.Device;
import com.asdf.myhomeback.repositories.DeviceRepository;
import com.asdf.myhomeback.services.DeviceService;
import com.asdf.myhomeback.utils.DeviceUtils;
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

    @Override
    public Device findOneByName(String name) throws DeviceException {
        Device device = deviceRepository.findByName(name);
        if(device == null)
            throw new DeviceException(String.format("Device with name: '%s' does not exist in database.", name));
        return deviceRepository.findByName(name);
    }

    @Override
    public void updateDeviceReadPeriod(Device d) throws DeviceException {
        DeviceUtils.checkReadPeriod(d.getReadPeriod());
        Device device = deviceRepository.findByName(d.getName());
        if (device == null)
            throw new DeviceException(String.format("Device with name: '%s' does not exist in database.", d.getName()));
        device.setReadPeriod(d.getReadPeriod());
        deviceRepository.save(device);
    }

}
