package com.tailspin.taskmanager.listiner.chat;

import com.tailspin.taskmanager.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListiner {
    @Autowired
    private SimpMessageSendingOperations messageTemplate;
    
    
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        String username = (String) StompHeaderAccessor.wrap(event.getMessage()).getSessionAttributes().get("username");
        System.out.println("User Connected: " + username);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            System.out.println("User Disconnected : " + username);
        }
    }


}
