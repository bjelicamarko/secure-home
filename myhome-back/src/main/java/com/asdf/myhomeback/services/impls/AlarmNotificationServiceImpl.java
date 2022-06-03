package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.models.AlarmNotification;
import com.asdf.myhomeback.repositories.AlarmNotificationRepository;
import com.asdf.myhomeback.services.AlarmNotificationService;
import com.asdf.myhomeback.services.AppUserService;
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
        if(appUserService.findByUsernameVerifiedUnlocked(username) == null)
            throw new AppUserException(String.format("Username sent from front: '%s' is invalid (non-existent/locked/deleted user)", username));

        Page<AlarmNotification> anp = alarmNotificationRepository.findAllByUsername(username, pageable);
        List<AlarmNotification> ans = anp.getContent();

        ans.forEach(alarmNotification -> alarmNotification.setSeen(true));
        alarmNotificationRepository.saveAll(ans);

        return anp;
    }

    @Override
    public int countNotSeenForUsername(String username) throws AppUserException {
        if(appUserService.findByUsernameVerifiedUnlocked(username) == null)
            throw new AppUserException(String.format("Username sent from front: '%s' is invalid (non-existent/locked/deleted user)", username));

        return alarmNotificationRepository.countAlarmNotificationByUsernameAndSeenIsFalse(username);
    }
}
