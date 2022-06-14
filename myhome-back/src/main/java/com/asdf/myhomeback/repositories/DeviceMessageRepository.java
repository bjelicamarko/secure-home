package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.DeviceMessage;
import com.asdf.myhomeback.models.enums.MessageStatus;
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

    @Query("select d from DeviceMessage d where d.deviceName = ?1 and d.timestamp >= ?2 and " +
            "d.timestamp < ?3 and (cast(d.messageStatus as string) like ?4 or ?4 = '') " +
            "order by d.id desc")
    List<DeviceMessage> createReport(String deviceName, long startDate, long endDate, String status);

    Page<DeviceMessage> findAllByDeviceNameInOrderByIdDesc(String[] deviceNames, Pageable pageable);

    Page<DeviceMessage> findAllByDeviceNameInAndTimestampBetweenAndMessageStatusOrderByIdDesc
            (String[] deviceNames, long start, long end, MessageStatus status, Pageable pageable);

    List<DeviceMessage> findAllByDeviceNameInAndTimestampBetweenAndMessageStatusOrderByIdDesc
            (String[] deviceNames, long start, long end, MessageStatus status);

    Page<DeviceMessage> findAllByDeviceNameInAndTimestampBetweenOrderByIdDesc
            (String[] deviceNames, long start, long end, Pageable pageable);

    List<DeviceMessage> findAllByDeviceNameInAndTimestampBetweenOrderByIdDesc
            (String[] deviceNames, long start, long end);
}
