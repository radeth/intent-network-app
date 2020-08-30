package com.company.model;

import java.util.List;

public class ConnectionListModel implements Cloneable{


    public static ConnectionListModel  copyConnectionList(ConnectionListModel connectionListModel){
        ConnectionListModel copiedList = new ConnectionListModel();
        copiedList.connectionsList = connectionListModel.connectionsList;
        copiedList.totalCost = connectionListModel.totalCost;
        return copiedList;
    }

    private List<Long> connectionsList;
    private Long totalCost;


    public List<Long> getConnectionsList() {
        return connectionsList;
    }

    public void setConnectionsList(List<Long> connectionsList) {
        this.connectionsList = connectionsList;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public void makeConnection(ConnectionModel connectionModel) {
        this.connectionsList.add(connectionModel.getConnectedDeviceId());
        setTotalCost(getTotalCost() + connectionModel.getCost());
    }

    public Long getLastDeviceId() {
        return getConnectionsList().get(getConnectionsList().size() - 1);
    }

    @Override
    public ConnectionListModel clone() throws CloneNotSupportedException {
        return (ConnectionListModel) super.clone();
    }
}
