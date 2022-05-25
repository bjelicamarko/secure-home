package com.asdf.myhomeback.services;

import com.asdf.myhomeback.models.DeviceMessage;

import java.util.List;

public interface DeviceMessageService {

    void save(DeviceMessage deviceMessage);

    void saveAll(List<DeviceMessage> deviceMessages);
}
