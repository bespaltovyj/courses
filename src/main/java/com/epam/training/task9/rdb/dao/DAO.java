package com.epam.training.task9.rdb.dao;

import com.epam.training.task7.record.Record;
import com.epam.training.task9.rdb.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public abstract class DAO {

    protected final ConnectionPool connectionPool;

    public DAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public abstract List<? extends Record> getEntities() throws InterruptedException;

    protected abstract Record getEntity(ResultSet resultSet) throws SQLException, InterruptedException;

}
