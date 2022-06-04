package com.asdf.myhomeback.services;

import com.asdf.myhomeback.models.Device;

import java.util.List;
import java.util.Set;

public interface DeviceService {

    List<Device> findAll();

    Set<Device> findAllByNameInList(List<String> names);

    Device findOneByName(String name);
}
