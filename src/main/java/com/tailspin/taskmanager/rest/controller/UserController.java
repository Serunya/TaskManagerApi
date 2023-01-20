package com.tailspin.taskmanager.rest.controller;

import com.tailspin.taskmanager.model.user.User;
import com.tailspin.taskmanager.model.user.UserName;
import com.tailspin.taskmanager.rest.payload.response.UserResponse;
import com.tailspin.taskmanager.security.jwt.JwtUtils;
import com.tailspin.taskmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/users/")
public class UserController {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private UserService userService;

    @GetMapping(path = "/getUserById")
    public ResponseEntity<UserResponse> getUserBId(@RequestParam int userId){
        User user = userService.getById(userId);
        if(user != null) {
            UserResponse response = new UserResponse(user.id(),user.firstName(), user.secondName());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.unprocessableEntity().build();
    }


    @GetMapping(path = "/getAllUser")
    public ResponseEntity<List<UserName>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }


    @GetMapping(path = "/getUserName")
    public ResponseEntity<UserResponse> getUserName(@RequestHeader("Authorization") String token){
        String login = jwtUtils.getLoginFromJwtToken(token.substring(7,token.length()));
        User user = userService.getByLogin(login);
        if(user != null) {
            UserResponse response = new UserResponse(user.id(),user.firstName(), user.secondName());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
