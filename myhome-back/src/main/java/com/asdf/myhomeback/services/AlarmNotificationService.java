package com.asdf.myhomeback.services;

import com.asdf.myhomeback.models.AlarmNotification;

import java.util.List;

public interface AlarmNotificationService {

    void save(AlarmNotification alarmNotification);

    void saveAll(List<AlarmNotification> alarmNotifications);
}
