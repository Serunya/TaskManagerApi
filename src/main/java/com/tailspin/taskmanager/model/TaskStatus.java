package com.tailspin.taskmanager.model;

public record TaskStatus(
        int id,
        int taskId,
        String status
) {
}
