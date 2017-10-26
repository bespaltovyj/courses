package com.epam.training.task7.record;

import com.epam.training.task7.record.AuthorRecord;
import com.epam.training.task7.record.BookRecord;
import com.epam.training.task7.record.PublisherRecord;

import java.util.List;

public class DataRecord {

    private List<AuthorRecord> authors;
    private List<BookRecord> books;
    private List<PublisherRecord> publishers;

    public DataRecord(List<AuthorRecord> authors, List<BookRecord> books, List<PublisherRecord> publishers) {
        this.authors = authors;
        this.books = books;
        this.publishers = publishers;
    }

    public List<AuthorRecord> getAuthors() {
        return authors;
    }

    public List<BookRecord> getBooks() {
        return books;
    }

    public List<PublisherRecord> getPublishers() {
        return publishers;
    }
}
