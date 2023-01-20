package com.tailspin.taskmanager.rest.payload.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tailspin.taskmanager.model.Point;

import java.util.List;

public class PointsRequest {
    List<point> points;

    @JsonCreator
    public PointsRequest(List<point> points) {
        this.points = points;
    }

    public List<point> getPoints() {
        return points;
    }

    public void setPoints(List<point> points) {
        this.points = points;
    }


    public static class point{
        String content;
        int taskId;
        boolean isDone;

        public Point toPoint(){
            return new Point(-1,content,isDone,taskId);
        }

        @JsonCreator
        public point(String content, int taskId, boolean isDone) {
            this.content = content;
            this.taskId = taskId;
            this.isDone = isDone;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public boolean isDone() {
            return isDone;
        }

        public void setDone(boolean done) {
            isDone = done;
        }
    }
}
