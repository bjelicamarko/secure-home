package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.DeviceMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceMessageRepository extends JpaRepository<DeviceMessage, Long> {

    @Query("select d from DeviceMessage d where d.deviceName = ?1 order by d.id desc")
    List<DeviceMessage> getAllMessagesFromDevice(String deviceName);

    @Query("select d from DeviceMessage d where d.timestamp >= ?1 and " +
            "d.timestamp < ?2 and (cast(d.messageStatus as string) like ?3 or ?3 = '') " +
            "order by d.id desc")
    List<DeviceMessage> filterMessages(long startDate, long endDate, String status);
}
