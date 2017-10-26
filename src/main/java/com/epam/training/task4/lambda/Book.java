package com.epam.training.task4.lambda;

import java.time.LocalDate;
import java.util.List;

public class Book {

    private String name;
    private LocalDate dateOfRelease;
    private List<Author> authors;

    public Book(String name, LocalDate dateOfRelease, List<Author> authors) {
        this.name = name;
        this.dateOfRelease = dateOfRelease;
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfRelease() {
        return dateOfRelease;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "{name='" + name + '\'' + ", dateOfRelease=" + dateOfRelease + '}';
    }
}
