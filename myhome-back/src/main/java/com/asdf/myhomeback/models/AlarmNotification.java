package com.asdf.myhomeback.models;

import com.asdf.myhomeback.models.enums.AlarmType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AlarmNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "message", nullable=false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_type", nullable = false)
    private AlarmType alarmType;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "timestamp", nullable = false)
    private Long timestamp;

    @Column(name = "username", nullable = false)
    private String username;

    public AlarmNotification(String message, AlarmType alarmType, String deviceName, String username){
        this.message = message;
        this.alarmType = alarmType;
        this.deviceName = deviceName;
        this.timestamp = (new Date()).getTime();
        this.username = username;
    }

}
