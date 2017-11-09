package com.epam.training.task9.rdb.dao;

import com.epam.training.Log;
import com.epam.training.task7.data.Gender;
import com.epam.training.task7.record.AuthorRecord;
import com.epam.training.task7.record.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO extends DAO {

    public AuthorDAO(Connection connection) {
        super(connection);
    }

    public List<AuthorRecord> getEntities() {
        List<AuthorRecord> authorRecords = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            final String query = "SELECT * FROM AUTHOR;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                AuthorRecord authorRecord = getEntity(resultSet);
                authorRecords.add(authorRecord);
            }
        } catch (SQLException e) {
            Log.log.error(e);
        }
        return authorRecords;
    }

    protected AuthorRecord getEntity(ResultSet resultSet) throws SQLException {
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

    public void insertAuthorsInTable(List<AuthorRecord> authorRecords) {
        try (Statement statement = connection.createStatement()) {
            String values = authorRecords
                    .stream()
                    .map(AuthorDAO::getRepresentationAuthorAsString)
                    .reduce((x, y) -> x + "," + y)
                    .get();
            statement.executeUpdate(String.format("INSERT INTO AUTHOR VALUES %s;", values));
        } catch (SQLException e) {
            Log.log.error(e);
        }
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
