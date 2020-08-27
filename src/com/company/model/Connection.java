package com.company.model;

public class Connection {
    private Long deviceId;
    private Long cost;
    private Long connectedDeviceId;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Long getConnectedDeviceId() {
        return connectedDeviceId;
    }

    public void setConnectedDeviceId(Long connectedDeviceId) {
        this.connectedDeviceId = connectedDeviceId;
    }
}
