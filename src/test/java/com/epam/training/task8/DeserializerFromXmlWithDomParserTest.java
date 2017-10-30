package com.epam.training.task8;

import com.epam.training.Filler;
import com.epam.training.task7.data.Author;
import com.epam.training.task7.data.Book;
import com.epam.training.task7.data.Data;
import com.epam.training.task7.data.Publisher;
import com.epam.training.task7.processing.Deserializer;
import com.epam.training.task8.dom.DeserializerFromXmlWithDomParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class DeserializerFromXmlWithDomParserTest {

    static final String packageName = "com.epam.training.task8";

    @Test
    void deserialize() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("test.xml");
        final File file = new File(url.getFile());
        Deserializer deserializer = new DeserializerFromXmlWithDomParser();
        Data data = deserializer.deserialize(file);
        data.print();
        Data testData = Filler.testData();
        assertEquals(data, testData);
    }

}