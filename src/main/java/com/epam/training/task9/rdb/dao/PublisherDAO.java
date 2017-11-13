package com.epam.training.task9.rdb.dao;

import com.epam.training.Log;
import com.epam.training.task7.record.PublisherRecord;
import com.epam.training.task7.record.Record;
import com.epam.training.task9.rdb.ConnectionPool;
import com.epam.training.task9.rdb.ConnectionWrapper;

import java.sql.PreparedStatement;
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
            final String queryForPublishers = "SELECT P.id FROM PUBLISHER P;";
            ResultSet resultSetPublishers = statement.executeQuery(queryForPublishers);
            while (resultSetPublishers.next()) {
                PublisherRecord publisherRecord = parseEntity(resultSetPublishers);
                publisherRecords.add(publisherRecord);
                Log.traceLogger.info("PublisherRecord " + publisherRecord.getId() + " is created");
            }
        } catch (SQLException e) {
            Log.log.error(e);
        }
        connectionPool.relieveConnection(connection.getId());
        return publisherRecords;
    }

    protected PublisherRecord parseEntity(ResultSet resultSetPublishers) throws SQLException, InterruptedException {
        String id = resultSetPublishers.getString("id");
        List<String> booksId = getBooksIdByPublisher(id);
        return new PublisherRecord(id, booksId);
    }

    private List<String> getBooksIdByPublisher(String publisherId) throws SQLException, InterruptedException {
        List<String> booksId = new ArrayList<>();
        ConnectionWrapper connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            final String query = String.format("SELECT BP.book_id FROM BOOK_PUBLISHER BP WHERE BP.publisher_id='%s';", publisherId);
            ResultSet resultBooksId = statement.executeQuery(query);
            while (resultBooksId.next()) {
                booksId.add(resultBooksId.getString("book_id"));
            }
        }
        connectionPool.relieveConnection(connection.getId());
        return booksId;
    }

    @Override
    public PublisherRecord getEntityById(String id) throws InterruptedException {
        ConnectionWrapper connection = connectionPool.getConnection();
        PublisherRecord publisherRecord = null;
        final String query = "SELECT P.id FROM PUBLISHER P WHERE P.id=?";
        try (PreparedStatement preparedStatement = connection.preparedStatement(query)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                publisherRecord = parseEntity(resultSet);
                Log.traceLogger.info("PublisherRecord " + publisherRecord.getId() + " is created");
            }
        } catch (SQLException e) {
            Log.log.error(e);
        }
        connectionPool.relieveConnection(connection.getId());
        return publisherRecord;
    }

    public void insertPublishersIntoTables(List<PublisherRecord> publisherRecords) throws SQLException, InterruptedException {
        ConnectionWrapper connection = connectionPool.getConnection();
        connection.setAutoCommit(false);
        try (Statement statement = connection.createStatement()) {
            String queryForInsertInPublisher = String.format("INSERT INTO PUBLISHER VALUES %s;", getStringRepresentationOfPublishers(publisherRecords));
            statement.executeUpdate(queryForInsertInPublisher);
        }
        try (Statement statement = connection.createStatement()) {
            String queryForInsertInBookPublisher = String.format("INSERT INTO Book_Publisher VALUES %s;", getStringRepresentationBooksIdAndPublishersId(publisherRecords));
            statement.executeUpdate(queryForInsertInBookPublisher);
        }
        connection.commit();
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
