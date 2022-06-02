package com.asdf.myhomeback.websocket;

import com.asdf.myhomeback.models.AlarmNotification;
import com.asdf.myhomeback.models.enums.AlarmType;

import java.util.List;

public interface WebSocketService {

    void sendNotifications(List<AlarmNotification> orderNotifications, AlarmType type);

}
