package com.epam.training.task7.record;

import com.epam.training.task7.data.Publisher;

import java.util.List;
import java.util.stream.Collectors;

public class PublisherRecord extends Record{

    private final String id;
    private List<String> bookIds;

    public PublisherRecord(String id, List<String> bookIds) {
        this.id = id;
        this.bookIds = bookIds;
    }

    public PublisherRecord(Publisher publisher) {
        this.bookIds = publisher.getBooks().stream().map(BookRecord::hashCode).collect(Collectors.toList());
        this.id = hashCode(publisher);
    }


    public String getId() {
        return id;
    }

    public List<String> getBookIds() {
        return bookIds;
    }

    public static String hashCode(Publisher publisher) {
        return String.valueOf(publisher.hashCode());
    }
}
