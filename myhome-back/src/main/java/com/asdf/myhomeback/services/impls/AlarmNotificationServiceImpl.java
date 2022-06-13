package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.exceptions.AlarmNotificationException;
import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.models.AlarmNotification;
import com.asdf.myhomeback.repositories.AlarmNotificationRepository;
import com.asdf.myhomeback.services.AlarmNotificationService;
import com.asdf.myhomeback.services.AppUserService;
import com.asdf.myhomeback.services.LogService;
import com.asdf.myhomeback.utils.LogMessGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmNotificationServiceImpl implements AlarmNotificationService {

    @Autowired
    private AlarmNotificationRepository alarmNotificationRepository;

    @Autowired
    private AppUserService appUserService;

    @Override
    public void save(AlarmNotification alarmNotification) {
        alarmNotificationRepository.save(alarmNotification);
    }

    @Override
    public void saveAll(List<AlarmNotification> alarmNotifications) {
        alarmNotificationRepository.saveAll(alarmNotifications);
    }

    @Override
    public Page<AlarmNotification> findAllByUsername(String username, Pageable pageable) throws AppUserException {
        if(appUserService.findByUsernameVerifiedUnlocked(username) == null && !username.equals("admin"))
            throw new AppUserException(String.format("Username sent from front: '%s' is invalid (non-existent/locked/deleted user)", username));

        return alarmNotificationRepository.findAllByUsername(username, pageable);
    }

    @Override
    public int countNotSeenForUsername(String username) throws AppUserException {
        if(appUserService.findByUsernameVerifiedUnlocked(username) == null && !username.equals("admin"))
            throw new AppUserException(String.format("Username sent from front: '%s' is invalid (non-existent/locked/deleted user)", username));

        return alarmNotificationRepository.countAlarmNotificationByUsernameAndSeenIsFalse(username);
    }

    @Override
    public Page<AlarmNotification> findAllByUsernameNotSeen(String username, Pageable pageable) throws AppUserException {
        if(appUserService.findByUsernameVerifiedUnlocked(username) == null && !username.equals("admin"))
            throw new AppUserException(String.format("Username sent from front: '%s' is invalid (non-existent/locked/deleted user)", username));

        return alarmNotificationRepository.findAllByUsernameAndSeenIsFalse(username, pageable);
    }

    @Override
    public void setSeen(String username, Long id) throws AlarmNotificationException, AppUserException {
        if(appUserService.findByUsernameVerifiedUnlocked(username) == null && !username.equals("admin"))
            throw new AppUserException(String.format("Username sent from front: '%s' is invalid (non-existent/locked/deleted user)", username));

        AlarmNotification alarmNotification = alarmNotificationRepository.findOneById(id);
        if(alarmNotification == null) {
            throw new AlarmNotificationException(String.format("Username: '%s' tried to mark as seen invalid alarm notification.", username));
        }

        if(!alarmNotification.getUsername().equals(username)) {
            throw new AlarmNotificationException(String.format("Username: '%s' tried to mark as seen alarm notification with id: '%s' from user:" +
                    "'%s'.", username, id, alarmNotification.getUsername()));
        }

        if(alarmNotification.getSeen()) {
            throw new AlarmNotificationException(String.format("Username: '%s' tried to mark as seen already seen " +
                    "alarm notification with id: '%s'.", username, id));
        }


        alarmNotification.setSeen(true);
        alarmNotificationRepository.save(alarmNotification);
    }

    @Override
    public void saveAllAndSetSeen(String username, List<AlarmNotification> alarmNotifications, LogService logService) {
        alarmNotifications.forEach(alarmNotification -> {
            if(!alarmNotification.getSeen()) {
                logService.generateInfoLog(LogMessGen.successfulUserNotificationSeen(username, alarmNotification.getId()));
                alarmNotification.setSeen(true);
            }
        });

        alarmNotificationRepository.saveAll(alarmNotifications);
    }
}
