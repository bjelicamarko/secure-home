package com.asdf.myhomeback.websocket;

import com.asdf.myhomeback.models.AlarmNotification;
import com.asdf.myhomeback.models.enums.AlarmType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendNotifications(List<AlarmNotification> alarmNotifications, AlarmType type) {

        for(AlarmNotification an : alarmNotifications) {
            Map<String, String> message = new HashMap<>();
            message.put("message", an.getMessage());

            this.simpMessagingTemplate.convertAndSend(String.format("/socket-publisher/%s", an.getUsername()), message);
        }

    }

}
