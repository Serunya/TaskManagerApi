package com.tailspin.taskmanager.websocket;


import com.tailspin.taskmanager.model.Message;
import com.tailspin.taskmanager.rest.payload.requests.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocket {

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public MessageRequest receiveMessage(MessageRequest message){
        return message;
    }



}
