package com.epam.training.task9;

import com.epam.training.task7.data.Data;
import com.epam.training.task7.processing.Deserializer;
import com.epam.training.task8.dom.DeserializerFromXmlWithDomParser;
import com.epam.training.task9.rdb.Util;
import com.epam.training.task9.rdb.processing.DeserializerFromRDB;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;


public class PersistenceTest {

    @Test
    void testDeserializerFromRDB() throws Exception {
        Util.createTablesAndFillFromXML("rdb.properties", "createTables.sql", "test.xml");

        URL urlXmlFile = Thread.currentThread().getContextClassLoader().getResource("test.xml");
        final File fileXml = new File(urlXmlFile.getFile());
        Deserializer deserializerXML = new DeserializerFromXmlWithDomParser();
        Data dataFromXml = deserializerXML.deserialize(fileXml);

        URL urlRDBPropertiesFile = Thread.currentThread().getContextClassLoader().getResource("rdb.properties");
        final File fileRDBProperties = new File(urlRDBPropertiesFile.getFile());

        Deserializer deserializerRDB = new DeserializerFromRDB(Util.CONNECTION_POOL);
        Data dataFromRDB = deserializerRDB.deserialize(fileRDBProperties);

        assertEquals(dataFromXml, dataFromRDB);
    }
}
