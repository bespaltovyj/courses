package com.epam.training.task7.record;

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
