package com.tailspin.taskmanager.rest.payload.requests;

import com.tailspin.taskmanager.model.Task;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class TaskRequest {

    private String title;
    private String description;
    private Integer executorId;
    private String deadLine;

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

    public Integer getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Integer executorId) {
        this.executorId = executorId;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public Task toTask(int curatorId){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date deadline = new Date(df.parse(deadLine).getTime());
            LocalDate now = LocalDate.now();
            String nowDate = now.getDayOfMonth() + "/" + now.getMonthValue()+"/"+now.getYear();
            Date startingdate = new Date(df.parse(nowDate).getTime());
            return new Task(-1,title,description,executorId,curatorId,deadline,startingdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
