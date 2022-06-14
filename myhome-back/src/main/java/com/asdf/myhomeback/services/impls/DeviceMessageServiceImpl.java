package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.exceptions.DeviceException;
import com.asdf.myhomeback.models.*;
import com.asdf.myhomeback.models.enums.AlarmType;
import com.asdf.myhomeback.models.enums.MessageStatus;
import com.asdf.myhomeback.repositories.DeviceMessageRepository;
import com.asdf.myhomeback.services.*;
import com.asdf.myhomeback.utils.DeviceUtils;
import com.asdf.myhomeback.websocket.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DeviceMessageServiceImpl implements DeviceMessageService {

    @Autowired
    private DeviceMessageRepository deviceMessageRepository;

    @Autowired
    private AlarmRuleService alarmRuleService;

    @Autowired
    private AlarmNotificationService alarmNotificationService;

    @Autowired
    private RealEstateService realEstateService;

    @Autowired
    private UserRealEstateService userRealEstateService;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private SymmetricKeyToolService symmetricKeyToolService;

    @Override
    public void save(DeviceMessage deviceMessage) {
        deviceMessageRepository.save(deviceMessage);
    }

    @Override
    public void saveAll(List<DeviceMessage> deviceMessages) throws Exception {
        // Decrypt messages
        decryptMessages(deviceMessages);

        List<AlarmRule> alarmRules = alarmRuleService.findAllByType(AlarmType.DEVICE);
        List<AlarmNotification> alarmNotifications = new ArrayList<>();
        alarmRules.forEach(alarmRule -> {
            deviceMessages.forEach(deviceMessage -> {
                // if deviceMessage->message contains pattern from rule
                // and alarmRule->deviceName is equals to deviceMessage->deviceName
                if (deviceMessage.getMessage().contains(alarmRule.getRulePattern()) && deviceMessage.getDeviceName().equals(alarmRule.getDeviceName())) {
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

        // save all notifications
        alarmNotificationService.saveAll(alarmNotifications);

        // send all notifications
        webSocketService.sendNotifications(alarmNotifications, AlarmType.DEVICE);

        deviceMessageRepository.saveAll(deviceMessages);
    }

    private String generateMessage(String message, String deviceName, String name) {
        return String.format("From %s in %s arrived message - %s", deviceName, name, message);
    }

    private void decryptMessages(List<DeviceMessage> deviceMessages) throws Exception  {
        for(DeviceMessage deviceMessage : deviceMessages) {
            System.err.println("Crypt: " + deviceMessage.getMessage());
            deviceMessage.setMessage(symmetricKeyToolService.decryptMessage(deviceMessage.getMessage()));
            System.err.println("Decrypt:" + deviceMessage.getMessage());
        }
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

    @Override
    public Page<DeviceMessage> findAllByDeviceNameInOrderByIdDesc(String nameOfRealEstate, Pageable pageable) {
        RealEstate re = realEstateService.findRealEstateByName(nameOfRealEstate);
        String[] arr = getAllDevicesNamesFromRealEstate(re);

        return deviceMessageRepository.findAllByDeviceNameInOrderByIdDesc(arr, pageable);
    }

    @Override
    public Page<DeviceMessage> filterAllMessages(String nameOfRealEstate, String startDate, String endDate, String selectedStatus
    , Pageable pageable) throws DeviceException {
        RealEstate re = realEstateService.findRealEstateByName(nameOfRealEstate);
        String[] arr = getAllDevicesNamesFromRealEstate(re);

        startDate = this.checkAllFromField(startDate);
        endDate = this.checkAllFromField(endDate);
        selectedStatus = this.checkAllFromField(selectedStatus);

        DeviceUtils.checkMessageStatus(selectedStatus);
        long startDateVal = DeviceUtils.checkStartDate(startDate);
        long endDateVal = DeviceUtils.checkEndDate(endDate);

        if (selectedStatus.equals("")) {
            return deviceMessageRepository.findAllByDeviceNameInAndTimestampBetweenOrderByIdDesc
                    (arr, startDateVal, endDateVal, pageable);
        } else {
            return deviceMessageRepository.findAllByDeviceNameInAndTimestampBetweenAndMessageStatusOrderByIdDesc
                    (arr, startDateVal, endDateVal, MessageStatus.valueOf(selectedStatus), pageable);
        }
    }

    @Override
    public List<DeviceMessage> createAllReport(String nameOfRealEstate,
                                               String startDate, String endDate, String selectedStatus) throws DeviceException {
        RealEstate re = realEstateService.findRealEstateByName(nameOfRealEstate);
        String[] arr = getAllDevicesNamesFromRealEstate(re);

        startDate = this.checkAllFromField(startDate);
        endDate = this.checkAllFromField(endDate);
        selectedStatus = this.checkAllFromField(selectedStatus);

        DeviceUtils.checkMessageStatus(selectedStatus);
        long startDateVal = DeviceUtils.checkStartDate(startDate);
        long endDateVal = DeviceUtils.checkEndDate(endDate);

        if (selectedStatus.equals("")) {
            return deviceMessageRepository.findAllByDeviceNameInAndTimestampBetweenOrderByIdDesc
                    (arr, startDateVal, endDateVal);
        } else {
            return deviceMessageRepository.findAllByDeviceNameInAndTimestampBetweenAndMessageStatusOrderByIdDesc
                    (arr, startDateVal, endDateVal, MessageStatus.valueOf(selectedStatus));
        }
    }


    private String [] getAllDevicesNamesFromRealEstate(RealEstate re) {
        String[] arr = new String[re.getDevices().size()];
        int i = 0;
        for (Device device : re.getDevices()) {
            arr[i] = device.getName();
            i++;
        }
        return arr;
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
