package com.tailspin.taskmanager.rest.payload.requests;

public class PointStatusRequest {
    int pointId;
    boolean status;


    public PointStatusRequest(int pointId, boolean status) {
        this.pointId = pointId;
        this.status = status;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
