package com.tailspin.taskmanager.model.user;

public record User(
    long id,
    String login,
    String password,
    String firstName,
    String secondName){
}
