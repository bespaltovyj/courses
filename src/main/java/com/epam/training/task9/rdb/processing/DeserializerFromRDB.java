package com.epam.training.task9.rdb.processing;

import com.epam.training.task7.data.Data;
import com.epam.training.task7.exception.LoadDataException;
import com.epam.training.task7.processing.Deserializer;
import com.epam.training.task7.processing.Transformation;
import com.epam.training.task7.record.AuthorRecord;
import com.epam.training.task7.record.BookRecord;
import com.epam.training.task7.record.DataRecord;
import com.epam.training.task7.record.PublisherRecord;
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
    @Override
    public Data deserialize(File fileProperties) throws LoadDataException {
        try {
            Connection connection = Util.createConnection(fileProperties);

            AuthorDAO authorDAO = new AuthorDAO(connection);
            List<AuthorRecord> authors = authorDAO.getAuthors();

            BookDAO bookDAO = new BookDAO(connection);
            List<BookRecord> books = bookDAO.getBooks();

            PublisherDAO publisherDAO = new PublisherDAO(connection);
            List<PublisherRecord> publishers = publisherDAO.getPublishers();

            DataRecord dataRecord = new DataRecord(authors, books, publishers);
            return Transformation.transformRecordToData(dataRecord);
        } catch (SQLException | IOException e) {
            throw new LoadDataException(e);
        }
    }
}
