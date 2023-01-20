package com.tailspin.taskmanager.rest.payload.response;

import com.tailspin.taskmanager.model.Task;

import java.sql.Date;

public class TaskResponse {
    int id;
    String title;
    String description;
    int executorId;
    int curratorId;
    Date deadLine;
    Date startingDate;
    String taskStatus;

    public TaskResponse(Task task, String taskStatus) {
        this.id = task.id();
        this.title = task.title();
        this.description = task.description();
        this.executorId = task.executorId();
        this.curratorId = task.curratorId();
        this.deadLine = task.deadLine();
        this.startingDate = task.startingDate();
        this.taskStatus = taskStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExecutorId() {
        return executorId;
    }

    public void setExecutorId(int executorId) {
        this.executorId = executorId;
    }

    public int getCurratorId() {
        return curratorId;
    }

    public void setCurratorId(int curratorId) {
        this.curratorId = curratorId;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}

