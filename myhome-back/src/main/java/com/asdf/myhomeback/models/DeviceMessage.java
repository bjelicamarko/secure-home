package com.asdf.myhomeback.models;

import com.asdf.myhomeback.dto.DeviceMessageDTO;
import com.asdf.myhomeback.models.enums.MessageStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Setter
@NoArgsConstructor
@Entity
public class DeviceMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable=false)
    private Long id;

    @NonNull
    @Column(name = "device_name", nullable = false)
    private String deviceName;

    @NonNull
    @Column(name = "timestamp_value", nullable = false)
    private Long timestamp;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "message_status", nullable = false)
    private MessageStatus messageStatus;

    @NonNull
    @Column(name = "message", nullable = false)
    private String message;

    public DeviceMessage(DeviceMessageDTO deviceMessageDTO) {
        this.deviceName = deviceMessageDTO.getDeviceName();
        this.timestamp = deviceMessageDTO.getTimestamp();
        this.messageStatus = MessageStatus.valueOf(deviceMessageDTO.getMessageStatus());
        this.message = deviceMessageDTO.getMessage();
    }
}
