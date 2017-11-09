package com.epam.training.task9.rdb.dao;

import com.epam.training.Log;
import com.epam.training.task7.record.PublisherRecord;
import com.epam.training.task9.rdb.ConnectionPool;
import com.epam.training.task9.rdb.ConnectionWrapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAO extends DAO {

    public PublisherDAO(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    public List<PublisherRecord> getEntities() throws InterruptedException {
        List<PublisherRecord> publisherRecords = new ArrayList<>();
        ConnectionWrapper connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            final String queryForPublishers = "SELECT * FROM PUBLISHER;";
            ResultSet resultSetPublishers = statement.executeQuery(queryForPublishers);
            while (resultSetPublishers.next()) {
                PublisherRecord publisherRecord = getEntity(resultSetPublishers);
                publisherRecords.add(publisherRecord);
            }
        } catch (SQLException e) {
            Log.log.error(e);
        }
        connectionPool.relieveConnection(connection.getId());
        return publisherRecords;
    }

    protected PublisherRecord getEntity(ResultSet resultSetPublishers) throws SQLException, InterruptedException {
        String id = resultSetPublishers.getString("id");
        List<String> booksId = getBooksIdByPublisher(id);
        return new PublisherRecord(id, booksId);
    }

    private List<String> getBooksIdByPublisher(String publisherId) throws SQLException, InterruptedException {
        List<String> booksId = new ArrayList<>();
        ConnectionWrapper connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            final String query = String.format("SELECT B.bookId FROM Book_Publisher B WHERE B.publisherId='%s';", publisherId);
            ResultSet resultBooksId = statement.executeQuery(query);
            while (resultBooksId.next()) {
                booksId.add(resultBooksId.getString("bookId"));
            }
        }
        connectionPool.relieveConnection(connection.getId());
        return booksId;
    }

    public void insertPublishersIntoTables(List<PublisherRecord> publisherRecords) throws SQLException, InterruptedException {
        ConnectionWrapper connection = connectionPool.getConnection();
        String queryForInsertInPublisher = String.format("INSERT INTO PUBLISHER VALUES %s;", getStringRepresentationOfPublishers(publisherRecords));
        connection.createStatement().executeUpdate(queryForInsertInPublisher);
        String queryForInsertInBookPublisher = String.format("INSERT INTO Book_Publisher VALUES %s;", getStringRepresentationBooksIdAndPublishersId(publisherRecords));
        connection.createStatement().executeUpdate(queryForInsertInBookPublisher);
        connectionPool.relieveConnection(connection.getId());
    }

    private static String getStringRepresentationOfPublishers(List<PublisherRecord> publisherRecords) {
        return publisherRecords.stream()
                .map(PublisherDAO::getStringRepresentationOfPublisher)
                .reduce((x, y) -> x + "," + y)
                .get();
    }

    private static String getStringRepresentationOfPublisher(PublisherRecord publisherRecords) {
        return String.format("(%s)", publisherRecords.getId());
    }

    private static String getStringRepresentationBooksIdAndPublishersId(List<PublisherRecord> publisherRecords) {
        return publisherRecords.stream()
                .flatMap(publisherRecord -> publisherRecord
                        .getBookIds()
                        .stream()
                        .map(bookId -> getStringRepresentationBookIdAndPublisherId(bookId, publisherRecord.getId())))
                .reduce((x, y) -> x + "," + y)
                .get();
    }

    private static String getStringRepresentationBookIdAndPublisherId(String bookId, String publisherId) {
        return String.format("(%s, %s)", bookId, publisherId);
    }
}
