package com.asdf.myhomeback.dto;


import com.asdf.myhomeback.models.AlarmNotification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlarmNotificationWithIdDTO extends AlarmNotificationDTO {

    private Long id;

    public AlarmNotificationWithIdDTO(AlarmNotification alarmNotification) {
        super(alarmNotification);
        this.id = alarmNotification.getId();
    }

}
