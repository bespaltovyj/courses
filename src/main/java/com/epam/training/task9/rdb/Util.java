package com.epam.training.task9.rdb;

import com.epam.training.task7.data.Data;
import com.epam.training.task7.processing.Deserializer;
import com.epam.training.task7.processing.Serializer;
import com.epam.training.task8.dom.DeserializerFromXmlWithDomParser;
import com.epam.training.task9.rdb.exception.CreationTableException;
import com.epam.training.task9.rdb.processing.SerializerInRDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    public static void executeSQLScript(File fileProperties, String nameOfSQLScript) throws CreationTableException {
        try {
            final String script = getSQLScriptByFileName(nameOfSQLScript);
            Connection connection = createConnection(fileProperties);
            connection.createStatement().execute(script);
            connection.close();
        } catch (CreationTableException e) {
            throw new CreationTableException("File " + nameOfSQLScript + " is invalid", e);
        } catch (SQLException e) {
            throw new CreationTableException("Script in file " + nameOfSQLScript + " is invalid", e);
        } catch (IOException e) {
            throw new CreationTableException(e);
        }
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

    public static Connection createConnection(File fileProperties) throws IOException, SQLException {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(fileProperties)) {
            properties.load(fileInputStream);
        }
        return DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
    }

    public static void fillTablesFromXMLFile(File fileProperties, String nameOfXMLFile) throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource(nameOfXMLFile);
        final File file = new File(url.getFile());
        Deserializer deserializer = new DeserializerFromXmlWithDomParser();
        Data data = deserializer.deserialize(file);
        Serializer serializer = new SerializerInRDB();
        serializer.serialize(data, fileProperties);
    }

    public static void createTablesAndFillFromXML(String nameOfRDBProperties, String nameOfSQLScript, String nameOfXml) throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource(nameOfRDBProperties);
        final File fileProperties = new File(url.getFile());
        Util.executeSQLScript(fileProperties, nameOfSQLScript);
        Util.fillTablesFromXMLFile(fileProperties, nameOfXml);
    }
}
