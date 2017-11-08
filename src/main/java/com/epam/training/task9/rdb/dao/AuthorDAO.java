package com.epam.training.task9.rdb.dao;

import com.epam.training.task7.data.Gender;
import com.epam.training.task7.record.AuthorRecord;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO extends DAO {

    public AuthorDAO(Connection connection) {
        super(connection);
    }

    public List<AuthorRecord> getAuthors() throws SQLException {
        final String query = "SELECT * FROM AUTHOR;";
        List<AuthorRecord> authorRecords = new ArrayList<>();
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        while (resultSet.next()) {
            AuthorRecord authorRecord = getAuthor(resultSet);
            authorRecords.add(authorRecord);
        }
        return authorRecords;
    }

    private AuthorRecord getAuthor(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        Date dateOfBirth = resultSet.getDate("dateOfBirth");
        Date dateOfDeath = resultSet.getDate("dateOfDeath");
        String gender = resultSet.getString("gender");
        return new AuthorRecord(
                id
                , name
                , dateOfBirth.toLocalDate()
                , dateOfDeath == null ? null : dateOfDeath.toLocalDate()
                , Gender.valueOf(gender)
        );
    }

    public void insertAuthorsInTable(List<AuthorRecord> authorRecords) throws SQLException {
        String values = authorRecords
                .stream()
                .map(AuthorDAO::getRepresentationAuthorAsString)
                .reduce((x, y) -> x + "," + y)
                .get();
        connection.createStatement().executeUpdate(String.format("INSERT INTO AUTHOR VALUES %s;", values));
    }

    private static String getRepresentationAuthorAsString(AuthorRecord authorRecord) {
        return String.format("('%s','%s',DATE '%s',%s,'%s')"
                , authorRecord.getId()
                , authorRecord.getName()
                , authorRecord.getDateOfBirth()
                , authorRecord.getDateOfDeath() == null ? null : "DATE '" + authorRecord.getDateOfDeath() + "'"
                , authorRecord.getGender()
        );
    }
}
