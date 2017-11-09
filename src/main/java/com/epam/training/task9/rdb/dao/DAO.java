package com.epam.training.task9.rdb.dao;

import com.epam.training.task7.record.Record;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public abstract class DAO {

    protected final Connection connection;

    public DAO(Connection connection) {
        this.connection = connection;
    }

    public abstract List<? extends Record> getEntities();
    protected abstract Record getEntity(ResultSet resultSet)  throws SQLException;

}
