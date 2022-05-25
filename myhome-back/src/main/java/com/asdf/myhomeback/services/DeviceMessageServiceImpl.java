package com.asdf.myhomeback.services;

import com.asdf.myhomeback.models.DeviceMessage;
import com.asdf.myhomeback.repositories.DeviceMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceMessageServiceImpl implements DeviceMessageService {

    @Autowired
    private DeviceMessageRepository deviceMessageRepository;

    @Override
    public void save(DeviceMessage deviceMessage) {
        deviceMessageRepository.save(deviceMessage);
    }

    @Override
    public void saveAll(List<DeviceMessage> deviceMessages) {
        deviceMessageRepository.saveAll(deviceMessages);
    }
}
