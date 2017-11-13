package com.epam.training.task9.rdb.dao;

import com.epam.training.Log;
import com.epam.training.task7.data.Gender;
import com.epam.training.task7.record.AuthorRecord;
import com.epam.training.task9.rdb.ConnectionPool;
import com.epam.training.task9.rdb.ConnectionWrapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO extends DAO {

    public AuthorDAO(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    public List<AuthorRecord> getEntities() throws InterruptedException {
        List<AuthorRecord> authorRecords = new ArrayList<>();
        ConnectionWrapper connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            final String query = "SELECT A.id,A.name,A.date_of_birth,A.date_of_death,A.gender FROM AUTHOR A;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                AuthorRecord authorRecord = parseEntity(resultSet);
                authorRecords.add(authorRecord);
                Log.traceLogger.info("AuthorRecord " + authorRecord.getId() + " is created");
            }
        } catch (SQLException e) {
            Log.log.error(e);
        }
        connectionPool.relieveConnection(connection.getId());
        return authorRecords;
    }

    @Override
    public AuthorRecord getEntityById(String id) throws InterruptedException {
        ConnectionWrapper connection = connectionPool.getConnection();
        AuthorRecord authorRecord = null;
        final String query = "SELECT A.id,A.name,A.date_of_birth,A.date_of_death,A.gender FROM AUTHOR A WHERE A.id=?";
        try (PreparedStatement preparedStatement = connection.preparedStatement(query)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                authorRecord = parseEntity(resultSet);
                Log.traceLogger.info("AuthorRecord " + authorRecord.getId() + " is created");
            }
        } catch (SQLException e) {
            Log.log.error(e);
        }
        connectionPool.relieveConnection(connection.getId());
        return authorRecord;
    }

    protected AuthorRecord parseEntity(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        Date dateOfBirth = resultSet.getDate("date_of_birth");
        Date dateOfDeath = resultSet.getDate("date_of_death");
        String gender = resultSet.getString("gender");
        return new AuthorRecord(
                id
                , name
                , dateOfBirth.toLocalDate()
                , dateOfDeath == null ? null : dateOfDeath.toLocalDate()
                , Gender.valueOf(gender)
        );
    }

    public void insertAuthorsInTable(List<AuthorRecord> authorRecords) throws InterruptedException {
        ConnectionWrapper connection = connectionPool.getConnection();
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
        connectionPool.relieveConnection(connection.getId());
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
