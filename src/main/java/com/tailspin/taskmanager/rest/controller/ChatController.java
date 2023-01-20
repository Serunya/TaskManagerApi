package com.tailspin.taskmanager.rest.controller;


import com.tailspin.taskmanager.model.Message;
import com.tailspin.taskmanager.model.user.User;
import com.tailspin.taskmanager.repository.MessageRepository;
import com.tailspin.taskmanager.repository.UserRepository;
import com.tailspin.taskmanager.rest.payload.requests.MessageRequest;
import com.tailspin.taskmanager.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path="api")
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;

    @MessageMapping("/chat.sendMessage")
    @SendTo("api/topic/public")
    public Message sendMessage(@RequestHeader String Authorize,@RequestBody MessageRequest message){
        String login = jwtUtils.getLoginFromJwtToken(Authorize);
        User user = userRepository.getByLogin(login);
        LocalDateTime date = LocalDateTime.now();
        Message chatMessage = new Message(-1,user.id(), message.getChatId(),new Date(date.getNano()),message.getContent());
        messageRepository.addMessage(chatMessage);
        return chatMessage;
    }

    public ResponseEntity addUser(@RequestHeader String Authorize, SimpMessageHeaderAccessor headerAccessor){
        String login = jwtUtils.getLoginFromJwtToken(Authorize);
        User user = userRepository.getByLogin(login);
        headerAccessor.getSessionAttributes().put("username",user.login());
        return ResponseEntity.ok("userSuccsesfulyAdd");
    }

    @GetMapping(path="chat/getMessageByChatId")
    public List<Message> getMessageByChatId(@RequestParam int chatId)
    {
        return messageRepository.getMessageByChatId(chatId);
    }
}