package com.asdf.myhomeback.dto;


import com.asdf.myhomeback.models.enums.MessageStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class DeviceMessageDTO {

    private String deviceName;
    private Long timestamp;
    private String messageStatus;
    private String message;
}
