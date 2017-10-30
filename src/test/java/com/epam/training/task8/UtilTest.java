package com.epam.training.task8;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;


class UtilTest {
    @Test
    void validateXmlWithXsd() throws IOException, SAXException {
        URL urlXml = Thread.currentThread().getContextClassLoader().getResource("test.xml");
        final File fileXml = new File(urlXml.getFile());
        URL urlSchema = Thread.currentThread().getContextClassLoader().getResource("schema.xsd");
        final File fileSchema = new File(urlSchema.getFile());
        assertTrue(Util.validateXmlWithXsd(fileXml, fileSchema));
    }

}