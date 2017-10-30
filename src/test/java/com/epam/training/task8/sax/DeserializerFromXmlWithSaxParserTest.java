package com.epam.training.task8.sax;

import com.epam.training.Filler;
import com.epam.training.task7.data.Data;
import com.epam.training.task7.processing.Deserializer;
import com.epam.training.task8.dom.DeserializerFromXmlWithDomParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class DeserializerFromXmlWithSaxParserTest {
    @Test
    void deserialize() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("test.xml");
        final File file = new File(url.getFile());
        Deserializer deserializer = new DeserializerFromXmlWithSaxParser();
        Data data = deserializer.deserialize(file);
        data.print();
        Data testData = Filler.testData();
        assertEquals(data, testData);
    }

}