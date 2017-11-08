package com.epam.training.task9.rdb.processing;

import com.epam.training.task7.data.Data;
import com.epam.training.task7.processing.Serializer;
import com.epam.training.task7.processing.Transformation;
import com.epam.training.task7.record.DataRecord;
import com.epam.training.task9.rdb.Util;
import com.epam.training.task9.rdb.dao.AuthorDAO;
import com.epam.training.task9.rdb.dao.BookDAO;
import com.epam.training.task9.rdb.dao.PublisherDAO;

import java.io.File;
import java.sql.Connection;

public class SerializerIntoRDB implements Serializer {

    @Override
    public void serialize(Data data, File fileProperties) throws Exception {
        DataRecord dataRecord = Transformation.transformDataToRecord(data);

        Connection connection = Util.createConnection(fileProperties);

        AuthorDAO authorDAO = new AuthorDAO(connection);
        authorDAO.insertAuthorsInTable(dataRecord.getAuthors());

        BookDAO bookDAO = new BookDAO(connection);
        bookDAO.insertBooksInTable(dataRecord.getBooks());

        PublisherDAO publisherDAO = new PublisherDAO(connection);
        publisherDAO.insertPublishersIntoTables(dataRecord.getPublishers());

        connection.close();
    }
}
