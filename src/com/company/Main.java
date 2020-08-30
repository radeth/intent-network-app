package com.company;

import com.company.model.ConnectionListModel;
import com.company.model.ConnectionModel;
import com.company.model.ConnectionType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
public class Main {


    private static Long sourceId = 2L;
    private static Long targetId = 7L;
    private static List<ConnectionListModel> connectionsWithCost = new ArrayList<>();
    private static List<ConnectionModel> connections = new ArrayList<>();
    private static ConnectionType connectionType;

    public static void main(String[] args) throws CloneNotSupportedException, IllegalAccessException, InstantiationException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                "C:\\Users\\rmoczarski\\Desktop\\intent-task\\src\\com\\company\\resource\\network"));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                if (line != null) {
                    ConnectionModel connection = new ConnectionModel();
                    connection.setDeviceId(java.lang.Long.parseLong(line.split(" ")[0]));
                    connection.setCost(java.lang.Long.parseLong(line.split(" ")[1]));
                    connection.setConnectedDeviceId(java.lang.Long.parseLong(line.split(" ")[2]));
                    connections.add(connection);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionsWithCost = createConnectionList(new ArrayList<>(), sourceId, targetId);
        ConnectionListModel connectionListModelCheapest =  Collections.min(connectionsWithCost, Comparator.comparing(ConnectionListModel::getTotalCost));
        System.out.println(connectionListModelCheapest.getConnectionsList());
    }

    private static List<ConnectionListModel> createConnectionList(List<ConnectionListModel> connectionListModels, Long sourceId, Long targetId) throws CloneNotSupportedException, InstantiationException, IllegalAccessException {
        if (connectionListModels.isEmpty()) {
            List<ConnectionModel> initialConnections = findConnectionByDeviceId(connections, sourceId);
            List<ConnectionListModel> initialConnectionListModels = new ArrayList<>();
            initialConnections.forEach(connectionModel -> {
                List<Long> initialList = new ArrayList<>();
                connectionModel.setConnectionDirection(sourceId);
                initialList.add(connectionModel.getDeviceId());
                initialList.add(connectionModel.getConnectedDeviceId());
                ConnectionListModel connectionListModel = new ConnectionListModel();
                connectionListModel.setTotalCost(connectionModel.getCost());
                connectionListModel.setConnectionsList(initialList);
                initialConnectionListModels.add(connectionListModel);
            });
            connectionListModels = initialConnectionListModels;
        }
        List<ConnectionListModel> newMultipleConnections = new ArrayList<>();
        List<ConnectionListModel> markedToDelete = new ArrayList<>();
        for (ConnectionListModel connectionListModel : connectionListModels) {
            switch (checkedConnectionType(connectionListModel, targetId)) {
                case CLOSED:
                    markedToDelete.add(connectionListModel);
                    break;
                case SINGLE_CONNECTION:
                    makeSingleConnection(connectionListModel);
                    break;
                case MULTIPLE_CONNECTION:
                    newMultipleConnections.addAll(createMultipleConnection(connectionListModel));
                    markedToDelete.add(connectionListModel);
                    break;
                default:
                    //todo-defeaut
                    break;

            }
        }
        if (markedToDelete.size() == 0) {
            return connectionListModels;
        } else {
            connectionListModels.removeAll(markedToDelete);
            connectionListModels.addAll(newMultipleConnections);
        }
        return createConnectionList(connectionListModels, sourceId, targetId);
    }

    private static List<ConnectionListModel> createMultipleConnection(ConnectionListModel connectionListModel) throws IllegalAccessException, InstantiationException {
        List<ConnectionListModel> newConnections = new ArrayList<>();
//        ConnectionListModel initialConnectionList = connectionListModel.getClass().newInstance();
        List<Long> sourceConnections = connectionListModel.getConnectionsList();
        Long initialCost = connectionListModel.getTotalCost();
//        initialConnectionList.setConnectionsList(new ArrayList<>(connectionListModel.getConnectionsList()));
//        initialConnectionList.setTotalCost(connectionListModel.getTotalCost());
        List<ConnectionModel> possibleConnection = findConnectionByDeviceId(connections, connectionListModel.getLastDeviceId());
        for (ConnectionModel connectionModel : possibleConnection) {
            ConnectionListModel newConnectionList = connectionListModel.getClass().newInstance();
            ;
            newConnectionList.setTotalCost(initialCost);
            newConnectionList.setConnectionsList(new ArrayList<>(sourceConnections));
            if (!newConnectionList.getConnectionsList().contains(connectionModel.getDeviceId()) ||
                !newConnectionList.getConnectionsList().contains(connectionModel.getConnectedDeviceId())) {

                connectionModel.setConnectionDirection(newConnectionList.getLastDeviceId());
                newConnectionList.makeConnection(connectionModel);
                newConnections.add(newConnectionList);
            }
        }
        return newConnections;
    }

    private static void makeSingleConnection(ConnectionListModel connectionListModel) {
        List<ConnectionModel> possibleConnection = findConnectionByDeviceId(connections, connectionListModel.getLastDeviceId());
        possibleConnection.forEach(connectionModel -> {
            if (!connectionListModel.getConnectionsList().contains(connectionModel.getDeviceId()) ||
                !connectionListModel.getConnectionsList().contains(connectionModel.getConnectedDeviceId())) {
                connectionModel.setConnectionDirection(connectionListModel.getLastDeviceId());
                connectionListModel.makeConnection(connectionModel);
            }
        });
    }

    private static ConnectionType checkedConnectionType(ConnectionListModel connectionListModel, Long targetId) {
        List<ConnectionModel> possibleConnection = findConnectionByDeviceId(connections, connectionListModel.getLastDeviceId());
        if(connectionListModel.getLastDeviceId().equals(targetId)){
            return ConnectionType.CLOSED_FINISHED;
        }
        if (possibleConnection.size() == 1) {
            return ConnectionType.CLOSED;
        }
        if (possibleConnection.size() == 2) {
            return ConnectionType.SINGLE_CONNECTION;
        } else {
            return ConnectionType.MULTIPLE_CONNECTION;
        }
    }


    private static List<ConnectionModel> findConnectionByDeviceId(List<ConnectionModel> connections, Long deviceId) {
        Predicate<ConnectionModel> byDeviceId = connection -> connection.getDeviceId().equals(deviceId) || connection.getConnectedDeviceId().equals(deviceId);
        return connections.stream().filter(byDeviceId).collect(Collectors.toList());
    }
}
