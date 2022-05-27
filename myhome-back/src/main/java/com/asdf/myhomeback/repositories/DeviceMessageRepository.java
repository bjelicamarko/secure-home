package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.DeviceMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DeviceMessageRepository extends JpaRepository<DeviceMessage, Long> {

    @Query("select d from DeviceMessage d where d.deviceName = ?1 order by d.id desc")
    Page<DeviceMessage> getAllMessagesFromDevice(String deviceName, Pageable pageable);

    @Query("select d from DeviceMessage d where d.deviceName = ?1 and d.timestamp >= ?2 and " +
            "d.timestamp < ?3 and (cast(d.messageStatus as string) like ?4 or ?4 = '') " +
            "order by d.id desc")
    Page<DeviceMessage> filterMessages(String deviceName, long startDate, long endDate, String status, Pageable pageable);
}
