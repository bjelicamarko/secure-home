package com.asdf.myhomeback.models;

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
    @Column(name = "name", unique = true, nullable = false)
    private String deviceName;

    @NonNull
    @Column(name = "timestamp", nullable = false)
    private Long timestamp;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "message_status", nullable = false)
    private MessageStatus messageStatus;

    @NonNull
    @Column(name = "message", nullable = false)
    private String message;
}
