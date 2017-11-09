package com.epam.training.task9.rdb.processing;

import com.epam.training.task7.data.Data;
import com.epam.training.task7.exception.LoadDataException;
import com.epam.training.task7.processing.Deserializer;
import com.epam.training.task7.processing.Transformation;
import com.epam.training.task7.record.AuthorRecord;
import com.epam.training.task7.record.BookRecord;
import com.epam.training.task7.record.DataRecord;
import com.epam.training.task7.record.PublisherRecord;
import com.epam.training.task9.rdb.ConnectionPool;
import com.epam.training.task9.rdb.Util;
import com.epam.training.task9.rdb.dao.AuthorDAO;
import com.epam.training.task9.rdb.dao.BookDAO;
import com.epam.training.task9.rdb.dao.PublisherDAO;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DeserializerFromRDB implements Deserializer {

    private ConnectionPool connectionPool;

    public DeserializerFromRDB(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Data deserialize(File fileProperties) throws LoadDataException {
        try {
            AuthorDAO authorDAO = new AuthorDAO(connectionPool);
            List<AuthorRecord> authors = authorDAO.getEntities();

            BookDAO bookDAO = new BookDAO(connectionPool);
            List<BookRecord> books = bookDAO.getEntities();

            PublisherDAO publisherDAO = new PublisherDAO(connectionPool);
            List<PublisherRecord> publishers = publisherDAO.getEntities();

            DataRecord dataRecord = new DataRecord(authors, books, publishers);
            return Transformation.transformRecordToData(dataRecord);
        } catch (InterruptedException e) {
            throw new LoadDataException(e);
        }
    }
}
