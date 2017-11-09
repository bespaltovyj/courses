package com.epam.training.task9.rdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class ConnectionWrapper {
    private final String id = UUID.randomUUID().toString();
    private Connection connection;

    public ConnectionWrapper(Connection connection) {
        this.connection = connection;
    }

    public String getId() {
        return id;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }

    public void commit() throws SQLException {
        connection.commit();
    }
}
