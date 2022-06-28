package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.AlarmNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlarmNotificationRepository extends JpaRepository<AlarmNotification, Long> {

    Page<AlarmNotification> findAllByUsername(String username, Pageable pageable);

    int countAlarmNotificationByUsernameAndSeenIsFalse(String username);

    Page<AlarmNotification> findAllByUsernameAndSeenIsFalse(String username, Pageable pageable);

    AlarmNotification findOneById(Long id);
}
