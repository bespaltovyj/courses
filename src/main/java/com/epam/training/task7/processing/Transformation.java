package com.epam.training.task7.processing;

import com.epam.training.task7.data.Author;
import com.epam.training.task7.data.Book;
import com.epam.training.task7.data.Data;
import com.epam.training.task7.data.Publisher;
import com.epam.training.task7.exception.ValidateDataException;
import com.epam.training.task7.record.AuthorRecord;
import com.epam.training.task7.record.BookRecord;
import com.epam.training.task7.record.DataRecord;
import com.epam.training.task7.record.PublisherRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Transformation {

    public static DataRecord transformDataToRecord(Data data) {
        List<AuthorRecord> authorRecords = new ArrayList<>();
        for (Author author : data.getAuthors()) {
            authorRecords.add(new AuthorRecord(author));
        }

        List<BookRecord> bookRecords = new ArrayList<>();
        for (Book book : data.getBooks()) {
            bookRecords.add(new BookRecord(book));
        }

        List<PublisherRecord> publisherRecords = new ArrayList<>();
        for (Publisher publisher : data.getPublishers()) {
            publisherRecords.add(new PublisherRecord(publisher));
        }

        return new DataRecord(authorRecords, bookRecords, publisherRecords);
    }

    public static Data transformRecordToData(DataRecord record) {
        Map<String, Author> authors = transformAuthorRecordsToAuthors(record.getAuthors());
        Map<String, Book> books = transformBookRecordsToBooks(record.getBooks(), authors);
        Map<String, Publisher> publishers = transformPublisherRecordsToPublisher(record.getPublishers(), books);
        Data data = new Data(
                new ArrayList<>(authors.values())
                , new ArrayList<>(books.values())
                , new ArrayList<>(publishers.values())
        );
        return data;
    }

    private static Map<String, Author> transformAuthorRecordsToAuthors(List<AuthorRecord> authorRecords) {
        Map<String, Author> authors = new HashMap<>();
        for (AuthorRecord authorRecord : authorRecords) {
            try {
                Author author = validateFieldsAndCreateAuthor(authorRecord);
                authors.put(authorRecord.getId(), author);
            } catch (ValidateDataException e) {
                //TODO FIX TO LOG
                System.out.println(e.getMessage());
            }
        }
        return authors;
    }

    private static Author validateFieldsAndCreateAuthor(AuthorRecord authorRecord) throws ValidateDataException {
        LocalDate dateOfBirth = authorRecord.getDateOfBirth();
        LocalDate dateOfDeath = authorRecord.getDateOfDeath();
        if (dateOfBirth != null && dateOfDeath != null && dateOfDeath.isBefore(dateOfBirth)) {
            throw new ValidateDataException("dateOfBirth late dateOfBirth of the author " + authorRecord.getId());
        }
        return new Author(authorRecord.getName(), dateOfBirth, dateOfDeath, authorRecord.getGender());
    }

    private static Map<String, Book> transformBookRecordsToBooks(List<BookRecord> bookRecords, Map<String, Author> authors) {
        Map<String, Book> books = new HashMap<>();
        for (BookRecord bookRecord : bookRecords) {
            try {
                Book book = validateFieldsAndCreateBook(bookRecord, authors);
                books.put(bookRecord.getId(), book);
            } catch (ValidateDataException e) {
                //TODO FIX TO LOG
                System.out.println(e.getMessage());
            }
        }
        return books;
    }

    private static Book validateFieldsAndCreateBook(BookRecord bookRecord, Map<String, Author> authors) throws ValidateDataException {
        List<Author> authorList = new ArrayList<>();
        for (String authorId : bookRecord.getAuthorsIds()) {
            Author author = authors.get(authorId);
            if (author == null) {
                throw new ValidateDataException("Book " + bookRecord.getId() + " references on a non-existent Author " + authorId);
            }
            authorList.add(author);
        }
        if (authorList.size() < 1) {
            throw new ValidateDataException("Book " + bookRecord.getId() + " has not authors");
        }
        return new Book(bookRecord.getName(), bookRecord.getDateOfRelease(), authorList);
    }

    private static Map<String, Publisher> transformPublisherRecordsToPublisher(List<PublisherRecord> publisherRecords, Map<String, Book> books) {
        Map<String, Publisher> publishers = new HashMap<>();
        for (PublisherRecord publisherRecord : publisherRecords) {
            try {
                Publisher publisher = validateFieldsAndCreatePublishers(publisherRecord, books);
                publishers.put(publisherRecord.getId(), publisher);
            } catch (ValidateDataException e) {
                //TODO FIX TO LOG
                System.out.println(e.getMessage());
            }
        }
        return publishers;
    }

    private static Publisher validateFieldsAndCreatePublishers(PublisherRecord publisherRecord, Map<String, Book> books) throws ValidateDataException {
        List<Book> bookList = new ArrayList<>();
        for (String bookId : publisherRecord.getBookIds()) {
            Book book = books.get(bookId);
            if (book == null) {
                throw new ValidateDataException("Publisher " + publisherRecord.getId() + " references on a non-existent Book " + bookId);
            }
            bookList.add(books.get(bookId));
        }
        return new Publisher(bookList);
    }
}
