package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.exceptions.DeviceException;
import com.asdf.myhomeback.models.AlarmNotification;
import com.asdf.myhomeback.models.AlarmRule;
import com.asdf.myhomeback.models.Device;
import com.asdf.myhomeback.models.DeviceMessage;
import com.asdf.myhomeback.models.enums.AlarmType;
import com.asdf.myhomeback.repositories.DeviceMessageRepository;
import com.asdf.myhomeback.services.AlarmNotificationService;
import com.asdf.myhomeback.services.AlarmRuleService;
import com.asdf.myhomeback.services.DeviceMessageService;
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

    @Autowired
    private AlarmRuleService alarmRuleService;

    @Autowired
    private AlarmNotificationService alarmNotificationService;

    @Override
    public void save(DeviceMessage deviceMessage) {
        deviceMessageRepository.save(deviceMessage);
    }

    @Override
    public void saveAll(List<DeviceMessage> deviceMessages) {
        List<AlarmRule> alarmRules = alarmRuleService.findAllByType(AlarmType.DEVICE);
        alarmRules.forEach(alarmRule -> {
            deviceMessages.forEach(deviceMessage -> {
                // if deviceMessage->message contains pattern from rule
                // and alarmRule->deviceName is equals to deviceMessage->deviceName
                if (deviceMessage.getMessage().contains(alarmRule.getRulePattern()) && deviceMessage.getDeviceName().equals(alarmRule.getDeviceName()))
                    alarmNotificationService.save(new AlarmNotification(deviceMessage.getMessage(), AlarmType.DEVICE, deviceMessage.getDeviceName()));
            });
        });
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
        return deviceMessageRepository.filterMessages(deviceName, startDateVal, endDateVal, selectedStatus, pageable);
    }

    @Override
    public List<DeviceMessage> createReport(String deviceName, String startDate, String endDate, String selectedStatus) throws DeviceException {
        startDate = this.checkAllFromField(startDate);
        endDate = this.checkAllFromField(endDate);
        selectedStatus = this.checkAllFromField(selectedStatus);

        DeviceUtils.checkMessageStatus(selectedStatus);
        long startDateVal = DeviceUtils.checkStartDate(startDate);
        long endDateVal = DeviceUtils.checkEndDate(endDate);
        return deviceMessageRepository.createReport(deviceName, startDateVal, endDateVal, selectedStatus);
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
