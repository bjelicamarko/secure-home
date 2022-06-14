package com.asdf.myhomeback.services;

import com.asdf.myhomeback.exceptions.DeviceException;
import com.asdf.myhomeback.models.DeviceMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeviceMessageService {

    void save(DeviceMessage deviceMessage);

    void saveAll(List<DeviceMessage> deviceMessages);

    Page<DeviceMessage> getAllMessagesFromDevice(String deviceName, Pageable pageable);

    Page<DeviceMessage> filterMessages(String deviceName,
                                       String startDate, String endDate, String selectedStatus, Pageable pageable) throws DeviceException;

    List<DeviceMessage> createReport(String deviceName, String startDate, String endDate, String selectedStatus) throws DeviceException;

    Page<DeviceMessage> findAllByDeviceNameInOrderByIdDesc(String nameOfRealEstate, Pageable pageable);

    Page<DeviceMessage> filterAllMessages(String nameOfRealEstate, String startDate, String endDate, String selectedStatus, Pageable pageable) throws DeviceException;

    List<DeviceMessage> createAllReport(String nameOfRealEstate, String startDate, String endDate, String selectedStatus) throws DeviceException;
}
