package com.epam.training.task7.data;

import java.io.Serializable;
import java.util.List;

public class Publisher implements Serializable {

    List<Book> books;

    public Publisher(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Publisher publisher = (Publisher) o;

        if (books.size() != publisher.books.size()) {
            return false;
        }
        if (!books.containsAll(publisher.books)) {
            return false;
        }
        if (!publisher.books.containsAll(books)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return books != null ? books.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Publisher{" + "books=" + books + '}';
    }
}
