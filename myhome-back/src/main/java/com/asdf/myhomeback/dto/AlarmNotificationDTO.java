package com.asdf.myhomeback.dto;

import com.asdf.myhomeback.models.AlarmNotification;
import com.asdf.myhomeback.models.enums.AlarmType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlarmNotificationDTO {
    private String message;
    private AlarmType alarmType;
    private String deviceName;
    private Long timestamp;
    private Boolean seen;

    public AlarmNotificationDTO(String message, AlarmType alarmType, String deviceName, Long timestamp, Boolean seen) {
        this.message = message;
        this.alarmType = alarmType;
        this.deviceName = deviceName;
        this.timestamp = timestamp;
        this.seen = seen;
    }

    public AlarmNotificationDTO(AlarmNotification alarmNotification) {
        this.message = alarmNotification.getMessage();
        this.alarmType = alarmNotification.getAlarmType();
        this.deviceName = alarmNotification.getDeviceName();
        this.timestamp = alarmNotification.getTimestamp();
        this.seen = alarmNotification.getSeen();
    }
}
