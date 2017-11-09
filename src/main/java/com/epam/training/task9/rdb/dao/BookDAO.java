package com.epam.training.task9.rdb.dao;

import com.epam.training.Log;
import com.epam.training.task7.record.BookRecord;
import com.epam.training.task9.rdb.ConnectionPool;
import com.epam.training.task9.rdb.ConnectionWrapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends DAO {

    public BookDAO(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    public List<BookRecord> getEntities() throws InterruptedException {
        List<BookRecord> bookRecords = new ArrayList<>();
        ConnectionWrapper connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            final String queryForBooks = "SELECT B.id,B.name,B.date_of_release FROM BOOK B;";
            ResultSet resultSetBooks = statement.executeQuery(queryForBooks);
            while (resultSetBooks.next()) {
                BookRecord bookRecord = getEntity(resultSetBooks);
                bookRecords.add(bookRecord);
                Log.traceLogger.info("BookRecord " + bookRecord.getId() + " is created");
            }
        } catch (SQLException e) {
            Log.log.error(e);
        }
        connectionPool.relieveConnection(connection.getId());
        return bookRecords;
    }

    protected BookRecord getEntity(ResultSet resultSetBooks) throws SQLException, InterruptedException {
        String id = resultSetBooks.getString("id");
        String name = resultSetBooks.getString("name");
        Date dateOfRelease = resultSetBooks.getDate("date_of_release");
        List<String> authorsId = getAuthorsIdByBook(id);
        return new BookRecord(id, name, dateOfRelease.toLocalDate(), authorsId);
    }

    private List<String> getAuthorsIdByBook(String bookId) throws InterruptedException {
        List<String> authorsId = new ArrayList<>();
        ConnectionWrapper connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            final String query = String.format("SELECT AB.author_id FROM AUTHOR_BOOK AB WHERE AB.book_id='%s';", bookId);
            ResultSet resultAuthorsId = statement.executeQuery(query);
            while (resultAuthorsId.next()) {
                authorsId.add(resultAuthorsId.getString("author_id"));
            }
        } catch (SQLException e) {
            Log.log.error(e);
        }
        connectionPool.relieveConnection(connection.getId());
        return authorsId;
    }

    public void insertBooksInTable(List<BookRecord> bookRecords) throws SQLException, InterruptedException {
        ConnectionWrapper connection = connectionPool.getConnection();
        connection.setAutoCommit(false);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("INSERT INTO BOOK VALUES %s;", getStringRepresentationOfBooks(bookRecords)));
        }
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(String.format("INSERT INTO AUTHOR_BOOK VALUES %s;", getStringRepresentationAuthorsIdAndBooksId(bookRecords)));
        }
        connection.commit();
        connectionPool.relieveConnection(connection.getId());
    }

    private static String getStringRepresentationOfBooks(List<BookRecord> bookRecords) {
        return bookRecords.stream()
                .map(BookDAO::getStringRepresentationOfBook)
                .reduce((x, y) -> x + "," + y)
                .get();
    }


    private static String getStringRepresentationOfBook(BookRecord bookRecord) {
        return String.format("('%s','%s',DATE '%s')"
                , bookRecord.getId()
                , bookRecord.getName()
                , bookRecord.getDateOfRelease()
        );
    }

    private static String getStringRepresentationAuthorsIdAndBooksId(List<BookRecord> bookRecords) {
        return bookRecords.stream()
                .flatMap(bookRecord -> bookRecord
                        .getAuthorsIds()
                        .stream()
                        .map(authorId -> getStringRepresentationAuthorIdAndBookId(authorId, bookRecord.getId())))
                .reduce((x, y) -> x + "," + y)
                .get();
    }

    private static String getStringRepresentationAuthorIdAndBookId(String authorId, String bookId) {
        return String.format("(%s, %s)", authorId, bookId);
    }

}
