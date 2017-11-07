package com.epam.training.task8.sax;

import com.epam.training.task7.data.Data;
import com.epam.training.task7.exception.LoadDataException;
import com.epam.training.task7.processing.Deserializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DeserializerFromXmlWithSaxParser implements Deserializer {
    @Override
    public Data deserialize(File xmlFile) throws LoadDataException {
        try {
            SaxHandler saxHandler = new SaxHandler();
            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(saxHandler);
            reader.parse(new InputSource(new FileInputStream(xmlFile)));
            return saxHandler.getData();
        } catch (FileNotFoundException e) {
            throw new LoadDataException("File " + xmlFile + " does not exist", e);
        } catch (IOException e) {
            throw new LoadDataException("IOException thrown when processing file " + xmlFile, e);
        } catch (SAXException e) {
            throw new LoadDataException("SAXException", e);
        }
    }
}
