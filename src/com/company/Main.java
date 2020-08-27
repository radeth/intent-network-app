package com.company;

import com.company.model.Connection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Connection> connections = new ArrayList<Connection>();

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(
                "C:\\Users\\rmoczarski\\Desktop\\intent-task\\src\\com\\company\\resource\\network"));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                if (line != null) {
                    Connection connection = new Connection();
                    connection.setDeviceId(Long.parseLong(line.split(" ")[0]));
                    connection.setCost(Long.parseLong(line.split(" ")[1]));
                    connection.setConnectedDeviceId(Long.parseLong(line.split(" ")[2]));
                    connections.add(connection);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(connections);
    }
}
