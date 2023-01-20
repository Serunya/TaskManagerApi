package com.tailspin.taskmanager.model;


public record Point(
        int id,
        String content,
        boolean isDone,
        int taskId) {
}
