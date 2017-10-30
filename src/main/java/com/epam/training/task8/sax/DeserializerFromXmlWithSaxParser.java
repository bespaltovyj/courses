package com.epam.training.task8.sax;

import com.epam.training.task7.data.Data;
import com.epam.training.task7.processing.Deserializer;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileInputStream;

public class DeserializerFromXmlWithSaxParser implements Deserializer {
    @Override
    public Data deserialize(File xmlFile) throws Exception {
        SaxHandler saxHandler = new SaxHandler();
        XMLReader reader = XMLReaderFactory.createXMLReader();
        reader.setContentHandler(saxHandler);
        reader.parse(new InputSource(new FileInputStream(xmlFile)));
        return saxHandler.getData();
    }
}
