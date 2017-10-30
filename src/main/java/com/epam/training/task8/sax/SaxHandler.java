package com.epam.training.task8.sax;

import com.epam.training.task7.data.Data;
import com.epam.training.task7.data.Gender;
import com.epam.training.task7.processing.Transformation;
import com.epam.training.task7.record.AuthorRecord;
import com.epam.training.task7.record.BookRecord;
import com.epam.training.task7.record.DataRecord;
import com.epam.training.task7.record.PublisherRecord;
import com.epam.training.task8.sax.buffer.AuthorRecordBuffer;
import com.epam.training.task8.sax.buffer.BookRecordBuffer;
import com.epam.training.task8.sax.buffer.PublisherRecordBuffer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaxHandler extends DefaultHandler {

    private boolean isOpenTagAuthor;
    private boolean isOpenTagBook;
    private boolean isOpenTagPublisher;

    private boolean isOpenTagAuthorId;
    private boolean isOpenTagAuthorName;
    private boolean isOpenTagAuthorDateOfBirth;
    private boolean isOpenTagAuthorDateOfDeath;
    private boolean isOpenTagAuthorGender;

    private boolean isOpenTagBookId;
    private boolean isOpenTagBookName;
    private boolean isOpenTagBookDateOfRelease;
    private boolean isOpenTagBookAuthorsId;

    private boolean isOpenTagPublisherId;
    private boolean isOpenTagPublisherBooksId;

    AuthorRecordBuffer authorRecord = new AuthorRecordBuffer();
    BookRecordBuffer bookRecord = new BookRecordBuffer();
    PublisherRecordBuffer publisherRecord = new PublisherRecordBuffer();

    List<AuthorRecord> authors = new ArrayList<>();
    List<BookRecord> books = new ArrayList<>();
    List<PublisherRecord> publishers = new ArrayList<>();


    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        setFlagIsOpenTagOnEntity(localName, true);
        setFlagIsOpenTagOnAuthorFields(localName, true);
        setFlagIsOpenTagOnBookFields(localName, true);
        setFlagIsOpenTagOnPublisherFields(localName, true);

    }

    @Override
    public void characters(char ch[], int start, int length) {
        String value = new String(ch, start, length);
        setFieldInAuthorRecord(value);
        setFieldInBookRecord(value);
        setFieldInPublisherRecord(value);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        setFlagIsOpenTagOnEntity(localName, false);
        setFlagIsOpenTagOnAuthorFields(localName, false);
        setFlagIsOpenTagOnBookFields(localName, false);
        setFlagIsOpenTagOnPublisherFields(localName, false);
        addEntityInList(localName);
    }

    private void setFlagIsOpenTagOnEntity(String tagName, boolean flag) {
        if ("author".equals(tagName)) {
            isOpenTagAuthor = flag;
        }
        if ("book".equals(tagName)) {
            isOpenTagBook = flag;
        }
        if ("publisher".equals(tagName)) {
            isOpenTagPublisher = flag;
        }
    }

    private void setFlagIsOpenTagOnAuthorFields(String name, boolean flag) {
        if (!isOpenTagAuthor) {
            return;
        }
        if ("id".equals(name)) {
            isOpenTagAuthorId = flag;
            return;
        }
        if ("name".equals(name)) {
            isOpenTagAuthorName = flag;
            return;
        }
        if ("dateOfBirth".equals(name)) {
            isOpenTagAuthorDateOfBirth = flag;
            return;
        }
        if ("dateOfDeath".equals(name)) {
            isOpenTagAuthorDateOfDeath = flag;
            return;
        }
        if ("gender".equals(name)) {
            isOpenTagAuthorGender = flag;
            return;
        }
    }

    private void setFlagIsOpenTagOnBookFields(String name, boolean flag) {
        if (!isOpenTagBook) {
            return;
        }
        if ("id".equals(name) && !isOpenTagBookAuthorsId) {
            isOpenTagBookId = flag;
            return;
        }
        if ("name".equals(name)) {
            isOpenTagBookName = flag;
            return;
        }
        if ("dateOfRelease".equals(name)) {
            isOpenTagBookDateOfRelease = flag;
            return;
        }
        if ("authorsId".equals(name)) {
            isOpenTagBookAuthorsId = flag;
            return;
        }
        if ("id".equals(name)) {
            isOpenTagAuthorId = flag;
            return;
        }
    }

    private void setFlagIsOpenTagOnPublisherFields(String name, boolean flag) {
        if (!isOpenTagPublisher) {
            return;
        }
        if ("id".equals(name) && !isOpenTagPublisherBooksId) {
            isOpenTagPublisherId = flag;
            return;
        }
        if ("booksId".equals(name)) {
            isOpenTagPublisherBooksId = flag;
            return;
        }
        if ("id".equals(name)) {
            isOpenTagBookId = flag;
            return;
        }
    }

    private void setFieldInAuthorRecord(String value) {
        if (!isOpenTagAuthor) {
            return;
        }
        if (isOpenTagAuthorId) {
            authorRecord.setId(value);
            return;
        }
        if (isOpenTagAuthorName) {
            authorRecord.setName(value);
            return;
        }
        if (isOpenTagAuthorDateOfBirth) {
            LocalDate dateOfDeath = value == null ? null : LocalDate.parse(value);
            authorRecord.setDateOfBirth(dateOfDeath);
            return;
        }
        if (isOpenTagAuthorDateOfDeath) {
            LocalDate dateOfDeath = "null".equals(value) ? null : LocalDate.parse(value);
            authorRecord.setDateOfDeath(dateOfDeath);
            return;
        }
        if (isOpenTagAuthorGender) {
            authorRecord.setGender(Gender.valueOf(value));
        }
    }

    private void setFieldInBookRecord(String value) {
        if (!isOpenTagBook) {
            return;
        }
        if (isOpenTagBookId) {
            bookRecord.setId(value);
            return;
        }
        if (isOpenTagBookName) {
            bookRecord.setName(value);
            return;
        }
        if (isOpenTagBookDateOfRelease) {
            LocalDate dateOfRelease = value == null ? null : LocalDate.parse(value);
            bookRecord.setDateOfRelease(dateOfRelease);
            return;
        }
        if (isOpenTagBookAuthorsId && isOpenTagAuthorId) {
            bookRecord.getAuthorsIds().add(value);
        }
    }

    private void setFieldInPublisherRecord(String value) {
        if (!isOpenTagPublisher) {
            return;
        }
        if (isOpenTagPublisherId) {
            publisherRecord.setId(value);
            return;
        }
        if (isOpenTagPublisherBooksId && isOpenTagBookId) {
            publisherRecord.getBookIds().add(value);
        }
    }

    private void addEntityInList(String tagName) {
        if ("author".equals(tagName)) {
            authors.add(authorRecord.getAuthorRecord());
            authorRecord.flushFields();
            return;
        }
        if ("book".equals(tagName)) {
            books.add(bookRecord.getBookRecord());
            bookRecord.flushFields();
            return;
        }
        if ("publisher".equals(tagName)) {
            publishers.add(publisherRecord.getPublisherRecord());
            publisherRecord.flushFields();
        }
    }

    public Data getData() {
        DataRecord dataRecord = new DataRecord(authors, books, publishers);
        return Transformation.transformRecordToData(dataRecord);
    }
}
