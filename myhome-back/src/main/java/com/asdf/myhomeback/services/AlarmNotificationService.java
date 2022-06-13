package com.asdf.myhomeback.services;

import com.asdf.myhomeback.exceptions.AlarmNotificationException;
import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.models.AlarmNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlarmNotificationService {

    void save(AlarmNotification alarmNotification);

    void saveAll(List<AlarmNotification> alarmNotifications);

    Page<AlarmNotification> findAllByUsername(String username, Pageable pageable) throws AppUserException;

    int countNotSeenForUsername(String username) throws AppUserException;

    Page<AlarmNotification> findAllByUsernameNotSeen(String username, Pageable pageable) throws AppUserException;

    void setSeen(String username, Long id) throws AlarmNotificationException, AppUserException;
}
