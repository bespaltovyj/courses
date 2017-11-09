package com.epam.training.task9.rdb;

import com.epam.training.Log;
import com.epam.training.task7.data.Data;
import com.epam.training.task7.processing.Deserializer;
import com.epam.training.task7.processing.Serializer;
import com.epam.training.task8.dom.DeserializerFromXmlWithDomParser;
import com.epam.training.task9.rdb.exception.CreationTableException;
import com.epam.training.task9.rdb.processing.SerializerIntoRDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Util {

    public final static ConnectionPool CONNECTION_POOL = createConnectionPool("rdb.properties");

    public static void executeSQLScript(String nameOfSQLScript) throws CreationTableException {
        try {
            final String script = getSQLScriptByFileName(nameOfSQLScript);
            ConnectionWrapper connection = CONNECTION_POOL.getConnection();
            connection.createStatement().execute(script);
            CONNECTION_POOL.relieveConnection(connection.getId());
        } catch (CreationTableException e) {
            throw new CreationTableException("File " + nameOfSQLScript + " is invalid", e);
        } catch (SQLException e) {
            throw new CreationTableException("Script in file " + nameOfSQLScript + " is invalid", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void executeQuery(String query) throws InterruptedException, SQLException {
        ConnectionWrapper connection = CONNECTION_POOL.getConnection();
        connection.createStatement().execute(query);
        CONNECTION_POOL.relieveConnection(connection.getId());
    }

    private static String getSQLScriptByFileName(String nameOfSQLScript) throws CreationTableException {
        URL urlToScript = Thread.currentThread().getContextClassLoader().getResource(nameOfSQLScript);
        try (FileInputStream fileInputStream = new FileInputStream(urlToScript.getFile())) {
            byte[] scriptInByteFormat = new byte[fileInputStream.available()];
            final int countReadBytes = fileInputStream.read(scriptInByteFormat);
            if (countReadBytes < 0) {
                return null;
            }
            return new String(scriptInByteFormat);
        } catch (FileNotFoundException e) {
            throw new CreationTableException("File " + nameOfSQLScript + " does not exist", e);
        } catch (IOException e) {
            throw new CreationTableException("IOException thrown when processing file " + nameOfSQLScript, e);
        }
    }

    private static ConnectionPool createConnectionPool(String nameOfFileProperties) {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(nameOfFileProperties);
            final File fileProperties = new File(url.getFile());
            ConnectionPool connectionPool = new ConnectionPool();
            connectionPool.addConnection(createConnection(fileProperties));
            connectionPool.addConnection(createConnection(fileProperties));
            connectionPool.addConnection(createConnection(fileProperties));
            return connectionPool;
        } catch (InterruptedException | IOException | SQLException e) {
            Log.log.fatal(e);
        }
        return null;
    }


    private static Connection createConnection(File fileProperties) throws IOException, SQLException {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(fileProperties)) {
            properties.load(fileInputStream);
        }
        return DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
    }

    public static void fillTablesFromXMLFile(String nameOfXMLFile) throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource(nameOfXMLFile);
        final File file = new File(url.getFile());
        Deserializer deserializer = new DeserializerFromXmlWithDomParser();
        Data data = deserializer.deserialize(file);
        Serializer serializer = new SerializerIntoRDB(CONNECTION_POOL);
        serializer.serialize(data, null);
    }

    public static void createTablesAndFillFromXML(String nameOfSQLScript, String nameOfXml) throws Exception {
        final String script = getSQLScriptByFileName(nameOfSQLScript);
        executeQuery(script);
        fillTablesFromXMLFile(nameOfXml);
    }
}
