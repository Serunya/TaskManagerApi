package com.tailspin.taskmanager.rest.payload.response;

public class TaskIdResponte {
    private int taskId;

    public TaskIdResponte(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
