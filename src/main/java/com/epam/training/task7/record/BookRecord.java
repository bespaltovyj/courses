package com.epam.training.task7.record;

import com.epam.training.task7.Configuration;
import com.epam.training.task7.data.Book;
import com.epam.training.task7.exception.LoadDataException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class BookRecord extends Record {

    private final String id;
    private String name;
    private LocalDate dateOfRelease;
    private List<String> authorsIds;

    public BookRecord(String id, String name, LocalDate dateOfRelease, List<String> authorsIds) {
        this.id = id;
        this.name = name;
        this.dateOfRelease = dateOfRelease;
        this.authorsIds = authorsIds;
    }

    public BookRecord(Book book) {
        this.name = book.getName();
        this.dateOfRelease = book.getDateOfRelease();
        this.authorsIds = book.getAuthors().stream().map(AuthorRecord::hashCode).collect(Collectors.toList());
        this.id = hashCode(book);
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfRelease() {
        return dateOfRelease;
    }

    public List<String> getAuthorsIds() {
        return authorsIds;
    }

    public static String hashCode(Book book) {
        return String.valueOf(book.hashCode());
    }
}
