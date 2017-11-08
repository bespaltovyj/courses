package com.epam.training.task9.rdb.dao;

import com.epam.training.task7.record.BookRecord;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends DAO {

    public BookDAO(Connection connection) {
        super(connection);
    }

    public List<BookRecord> getBooks() throws SQLException {
        final String queryForBooks = "SELECT * FROM BOOK;";
        List<BookRecord> bookRecords = new ArrayList<>();
        ResultSet resultSetBooks = connection.createStatement().executeQuery(queryForBooks);
        while (resultSetBooks.next()) {
            BookRecord bookRecord = getBook(resultSetBooks);
            bookRecords.add(bookRecord);
        }
        return bookRecords;
    }

    private BookRecord getBook(ResultSet resultSetBooks) throws SQLException {
        String id = resultSetBooks.getString("id");
        String name = resultSetBooks.getString("name");
        Date dateOfRelease = resultSetBooks.getDate("dateOfRelease");
        List<String> authorsId = getAuthorsIdByBook(id);
        return new BookRecord(id, name, dateOfRelease.toLocalDate(), authorsId);
    }

    private List<String> getAuthorsIdByBook(String bookId) throws SQLException {
        final String query = String.format("SELECT A.authorId FROM Author_Book A WHERE A.bookId='%s';", bookId);
        List<String> authorsId = new ArrayList<>();
        ResultSet resultAuthorsId = connection.createStatement().executeQuery(query);
        while (resultAuthorsId.next()) {
            authorsId.add(resultAuthorsId.getString("authorId"));
        }
        return authorsId;
    }

    public void insertBooksInTable(List<BookRecord> bookRecords) throws SQLException {
        connection.createStatement().executeUpdate(String.format("INSERT INTO BOOK VALUES %s;", getStringRepresentationOfBooks(bookRecords)));
        connection.createStatement().executeUpdate(String.format("INSERT INTO Author_Book VALUES %s;", getStringRepresentationAuthorsIdAndBooksId(bookRecords)));
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
