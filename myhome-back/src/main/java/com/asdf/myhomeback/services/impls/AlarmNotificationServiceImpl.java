package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.models.AlarmNotification;
import com.asdf.myhomeback.repositories.AlarmNotificationRepository;
import com.asdf.myhomeback.services.AlarmNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmNotificationServiceImpl implements AlarmNotificationService {

    @Autowired
    private AlarmNotificationRepository alarmNotificationRepository;

    @Override
    public void save(AlarmNotification alarmNotification) {
        alarmNotificationRepository.save(alarmNotification);
    }

    @Override
    public void saveAll(List<AlarmNotification> alarmNotifications) {
        alarmNotificationRepository.saveAll(alarmNotifications);
    }
}
