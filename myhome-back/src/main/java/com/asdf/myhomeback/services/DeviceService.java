package com.asdf.myhomeback.services;

import com.asdf.myhomeback.exceptions.DeviceException;
import com.asdf.myhomeback.models.Device;

import java.util.List;
import java.util.Set;

public interface DeviceService {

    List<Device> findAll();

    Set<Device> findAllByNameInList(List<String> names);

    Device findOneByName(String name) throws DeviceException;

    void updateDeviceReadPeriod(Device d) throws DeviceException;
}
