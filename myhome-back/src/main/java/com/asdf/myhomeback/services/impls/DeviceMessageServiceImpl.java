package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.exceptions.DeviceException;
import com.asdf.myhomeback.models.*;
import com.asdf.myhomeback.models.enums.AlarmType;
import com.asdf.myhomeback.models.enums.RuleType;
import com.asdf.myhomeback.repositories.DeviceMessageRepository;
import com.asdf.myhomeback.services.*;
import com.asdf.myhomeback.utils.DeviceUtils;
import com.asdf.myhomeback.websocket.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceMessageServiceImpl implements DeviceMessageService {

    @Autowired
    private DeviceMessageRepository deviceMessageRepository;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private AlarmNotificationService alarmNotificationService;

    @Autowired
    private RealEstateService realEstateService;

    @Autowired
    private UserRealEstateService userRealEstateService;

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public void save(DeviceMessage deviceMessage) {
        deviceMessageRepository.save(deviceMessage);
    }

    @Override
    public void saveAll(List<DeviceMessage> deviceMessages) {

        // checking databaseRules
        List<DeviceMessage> validDeviceMessages = getValidDeviceMessages(deviceMessages);

        // checking alarmRules
        List<AlarmNotification> alarmNotifications = getAlarmNotifications(validDeviceMessages);

        // save all notifications
        alarmNotificationService.saveAll(alarmNotifications);

        // send all notifications
        webSocketService.sendNotifications(alarmNotifications, AlarmType.DEVICE);

        deviceMessageRepository.saveAll(validDeviceMessages);
    }

    private List<DeviceMessage> getValidDeviceMessages(List<DeviceMessage> deviceMessages) {

        List<Rule> databaseRules = ruleService.findAll(RuleType.DATABASE);

        List<DeviceMessage> validDeviceMessages = new ArrayList<>();
        databaseRules.forEach(databaseRule -> {
            deviceMessages.forEach(deviceMessage -> {
                // if deviceMessage->message doesnt contain pattern from rule
                // and databaseRule->deviceName is equals to deviceMessage->deviceName
                if (deviceMessage.getMessage().matches(databaseRule.getRegexPattern()) && deviceMessage.getDeviceName().equals(databaseRule.getDeviceName())) {
                    validDeviceMessages.add(deviceMessage);
                }
            });
        });
        return  validDeviceMessages;
    }

    private List<AlarmNotification> getAlarmNotifications(List<DeviceMessage> deviceMessages) {
        List<Rule> alarmRules = ruleService.findAll(RuleType.ALARM);
        List<AlarmNotification> alarmNotifications = new ArrayList<>();
        alarmRules.forEach(alarmRule -> {
            deviceMessages.forEach(deviceMessage -> {
                // if deviceMessage->message contains pattern from rule
                // and alarmRule->deviceName is equals to deviceMessage->deviceName
                if (deviceMessage.getMessage().contains(alarmRule.getRegexPattern()) && deviceMessage.getDeviceName().equals(alarmRule.getDeviceName())) {
                    List<RealEstate> realEstates = realEstateService.getRealEstatesByDeviceName(alarmRule.getDeviceName());
                    realEstates.forEach(realEstate -> {
                        List<AppUser> usersFromRealEstate = userRealEstateService.getUsersFromRealEstate(realEstate.getName());
                        usersFromRealEstate.forEach(user -> {
                            String message = generateMessage(deviceMessage.getMessage(), alarmRule.getDeviceName(), realEstate.getName());
                            alarmNotifications.add(new AlarmNotification(message, AlarmType.DEVICE, deviceMessage.getDeviceName(), user.getUsername()));
                        });
                    });
                }
            });
        });
        return alarmNotifications;
    }

    private String generateMessage(String message, String deviceName, String name) {
        return String.format("From %s in %s arrived message - %s", deviceName, name, message);
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
