package com.asdf.myhomeback.services;

import com.asdf.myhomeback.exceptions.DeviceException;
import com.asdf.myhomeback.models.Device;
import com.asdf.myhomeback.models.DeviceMessage;
import com.asdf.myhomeback.repositories.DeviceMessageRepository;
import com.asdf.myhomeback.utils.DeviceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.*;
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

    @Override
    public Page<DeviceMessage> getAllMessagesFromDevice(String deviceName, Pageable pageable) {
        return deviceMessageRepository.getAllMessagesFromDevice(deviceName, pageable);
    }

    @Override
    public Page<DeviceMessage> filterMessages(String deviceName, String startDate, String endDate, String selectedStatus, Pageable pageable) throws DeviceException {
        startDate = this.checkAllFromField(startDate);
        endDate = this.checkAllFromField(endDate);
        selectedStatus = this.checkAllFromField(selectedStatus);

        DeviceUtils.checkMessageStatus(selectedStatus);
        long startDateVal = DeviceUtils.checkStartDate(startDate);
        long endDateVal = DeviceUtils.checkEndDate(endDate);
        System.out.println("START DATE " + startDateVal);
        System.out.println("END DATE " + endDateVal);
        return deviceMessageRepository.filterMessages(deviceName, startDateVal, endDateVal, selectedStatus, pageable);
    }

    private String checkAllFromField(String field) {
        if (field == null){
            field = "";
        }else if (field.equalsIgnoreCase("all")){
            field = "";
        }
        return field;
    }

}
