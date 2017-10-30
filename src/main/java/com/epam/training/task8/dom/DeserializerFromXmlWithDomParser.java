package com.epam.training.task8.dom;

import com.epam.training.task7.data.Data;
import com.epam.training.task7.data.Gender;
import com.epam.training.task7.exception.LoadDataException;
import com.epam.training.task7.processing.Deserializer;
import com.epam.training.task7.processing.Transformation;
import com.epam.training.task7.record.AuthorRecord;
import com.epam.training.task7.record.BookRecord;
import com.epam.training.task7.record.DataRecord;
import com.epam.training.task7.record.PublisherRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DeserializerFromXmlWithDomParser implements Deserializer {
    @Override
    public Data deserialize(File xmlFile) throws LoadDataException {
        try {
            List<AuthorRecord> authors = new ArrayList<>();
            List<BookRecord> books = new ArrayList<>();
            List<PublisherRecord> publishers = new ArrayList<>();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            Element documentElement = doc.getDocumentElement();

            for (int index = 0; index < documentElement.getChildNodes().getLength(); ++index) {
                Node node = documentElement.getChildNodes().item(index);
                if (node.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                Element array = (Element) node;
                String nameArray = array.getNodeName();
                switch (nameArray) {
                    case "authors":
                        authors = parseAuthors(array.getElementsByTagName("author"));
                        break;
                    case "books":
                        books = parseBooks(array.getElementsByTagName("book"));
                        break;
                    case "publishers":
                        publishers = parsePublishers(array.getElementsByTagName("publisher"));
                        break;
                    default:
                        throw new LoadDataException("Unknown  array name " + nameArray + "in the file " + xmlFile);
                }
            }
            DataRecord dataRecord = new DataRecord(authors, books, publishers);
            return Transformation.transformRecordToData(dataRecord);
        } catch (ParserConfigurationException e) {
            throw new LoadDataException("ParserConfigurationException in the file " + xmlFile, e);
        } catch (SAXException e) {
            throw new LoadDataException("SAXException in the file " + xmlFile, e);
        } catch (IOException e) {
            throw new LoadDataException("IOException in the file " + xmlFile, e);
        }
    }


    private List<AuthorRecord> parseAuthors(NodeList authors) {
        List<AuthorRecord> authorRecords = new ArrayList<>();
        for (int i = 0; i < authors.getLength(); ++i) {
            Node node = authors.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            AuthorRecord authorRecord = parseAuthor(node);
            authorRecords.add(authorRecord);
        }
        return authorRecords;
    }

    private AuthorRecord parseAuthor(Node node) {
        Element element = (Element) node;
        String id = getStringValueByTagName(element, "id");
        String name = getStringValueByTagName(element, "name");

        String dateOfBirthAsString = getStringValueByTagName(element, "dateOfBirth");
        LocalDate dateOfBirth = dateOfBirthAsString == null ? null : LocalDate.parse(dateOfBirthAsString);

        String dateOfDeathAsString = getStringValueByTagName(element, "dateOfDeath");
        LocalDate dateOfDeath = "null".equals(dateOfDeathAsString) ? null : LocalDate.parse(dateOfDeathAsString);

        String gender = getStringValueByTagName(element, "gender");

        return new AuthorRecord(id, name, dateOfBirth, dateOfDeath, Gender.valueOf(gender));
    }

    private List<BookRecord> parseBooks(NodeList books) {
        List<BookRecord> bookRecords = new ArrayList<>();
        for (int i = 0; i < books.getLength(); ++i) {
            Node node = books.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            BookRecord bookRecord = parseBook(node);
            bookRecords.add(bookRecord);
        }
        return bookRecords;
    }

    private BookRecord parseBook(Node node) {
        Element element = (Element) node;
        String id = getStringValueByTagName(element, "id");
        String name = getStringValueByTagName(element, "name");

        String dateOfReleaseAsString = getStringValueByTagName(element, "dateOfRelease");
        LocalDate dateOfRelease = dateOfReleaseAsString == null ? null : LocalDate.parse(dateOfReleaseAsString);
        List<String> authorsId = getListStringValueByTagName(element, "authorsId");
        return new BookRecord(id, name, dateOfRelease, authorsId);
    }

    private List<PublisherRecord> parsePublishers(NodeList publishers) {
        List<PublisherRecord> publisherRecords = new ArrayList<>();
        for (int i = 0; i < publishers.getLength(); ++i) {
            Node node = publishers.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            PublisherRecord publisherRecord = parsePublisher(node);
            publisherRecords.add(publisherRecord);
        }
        return publisherRecords;
    }

    private PublisherRecord parsePublisher(Node node) {
        Element element = (Element) node;
        String id = getStringValueByTagName(element, "id");
        List<String> booksId = getListStringValueByTagName(element, "booksId");
        return new PublisherRecord(id, booksId);
    }


    private String getStringValueByTagName(Element element, String tagName) {
        NodeList elementsByTagName = element.getElementsByTagName(tagName);
        //checking existence of fields
        if (elementsByTagName.getLength() == 0) {
            return null;
        }
        return elementsByTagName.item(0).getTextContent();

    }

    /**
     * Get list ids from tag <tagName>
     * Example:
     * <tagName>
     * <id>1</id>
     * <id>2</id>
     * </tagName>
     * Returns: List(1,2)
     */
    private List<String> getListStringValueByTagName(Element element, String tagName) {
        Element elementIds = (Element) element.getElementsByTagName(tagName).item(0);
        List<String> listIds = new ArrayList<>();
        NodeList nodeIdList = elementIds.getElementsByTagName("id");
        for (int i = 0; i < nodeIdList.getLength(); ++i) {
            Node nodeId = nodeIdList.item(i);
            if (nodeId.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element elementId = (Element) nodeId;
            String id = elementId.getTextContent();
            listIds.add(id);
        }
        return listIds;
    }


}
