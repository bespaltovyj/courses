package com.epam.training.task9.rdb.processing;

import com.epam.training.task7.data.Data;
import com.epam.training.task7.processing.Serializer;
import com.epam.training.task7.processing.Transformation;
import com.epam.training.task7.record.DataRecord;
import com.epam.training.task9.rdb.ConnectionPool;
import com.epam.training.task9.rdb.Util;
import com.epam.training.task9.rdb.dao.AuthorDAO;
import com.epam.training.task9.rdb.dao.BookDAO;
import com.epam.training.task9.rdb.dao.PublisherDAO;

import java.io.File;
import java.sql.Connection;

public class SerializerIntoRDB implements Serializer {

    private ConnectionPool connectionPool;

    public SerializerIntoRDB(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void serialize(Data data, File fileProperties) throws Exception {
        DataRecord dataRecord = Transformation.transformDataToRecord(data);

        AuthorDAO authorDAO = new AuthorDAO(connectionPool);
        authorDAO.insertAuthorsInTable(dataRecord.getAuthors());

        BookDAO bookDAO = new BookDAO(connectionPool);
        bookDAO.insertBooksInTable(dataRecord.getBooks());

        PublisherDAO publisherDAO = new PublisherDAO(connectionPool);
        publisherDAO.insertPublishersIntoTables(dataRecord.getPublishers());

    }
}
