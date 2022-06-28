package com.asdf.myhomeback.dto;


import com.asdf.myhomeback.models.DeviceMessage;
import com.asdf.myhomeback.models.enums.MessageStatus;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class DeviceMessageDTO {

    private String deviceName;
    private long timestamp;
    private String messageStatus;
    private String message;
    private String timestampString;


    public DeviceMessageDTO(DeviceMessage dm) {
        this.deviceName = dm.getDeviceName();
        LocalDateTime tempTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(dm.getTimestamp()),
                        TimeZone.getDefault().toZoneId());
        this.timestampString = tempTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.messageStatus = dm.getMessageStatus().toString();
        this.message = dm.getMessage();
    }
}
