package com.epam.training.task7.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Book implements Serializable {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!Objects.equals(name, book.name)) {
            return false;
        }
        if (!Objects.equals(dateOfRelease, book.dateOfRelease)) {
            return false;
        }
        if (authors.size() != book.getAuthors().size()) {
            return false;
        }
        if (!authors.containsAll(book.getAuthors())) {
            return false;
        }
        if (!book.getAuthors().containsAll(authors)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (dateOfRelease != null ? dateOfRelease.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", dateOfRelease=" + dateOfRelease +
                ", authors=" + authors +
                '}';
    }
}
