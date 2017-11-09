package com.epam.training.task9.rdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    Map<String, ConnectionWrapper> connections;
    BlockingQueue<ConnectionWrapper> queue;

    public ConnectionPool() {
        connections = new HashMap<>();
        queue = new ArrayBlockingQueue<>(10);
    }

    public ConnectionWrapper getConnection() throws InterruptedException {
        return queue.poll();
    }

    public void addConnection(Connection connection) throws InterruptedException {
        ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
        connections.put(connectionWrapper.getId(), connectionWrapper);
        queue.put(connectionWrapper);
    }

    public void closeConnections() throws SQLException {
        for (ConnectionWrapper connection : connections.values()) {
            connection.getConnection().close();
        }
    }

    public void relieveConnection(String id) throws InterruptedException {
        //connectionWrapper.setFree(true);
        ConnectionWrapper connectionWrapper = connections.get(id);
        queue.put(connectionWrapper);
    }
}
