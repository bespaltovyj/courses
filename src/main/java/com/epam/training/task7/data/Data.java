package com.epam.training.task7.data;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    private List<Author> authors;
    private List<Book> books;
    private List<Publisher> publishers;

    public Data(List<Author> authors, List<Book> books, List<Publisher> publishers) {
        this.authors = authors;
        this.books = books;
        this.publishers = publishers;
    }


    public List<Author> getAuthors() {
        return authors;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;
        return equalsList(authors, data.authors) && equalsList(books, data.books) && equalsList(publishers, data.publishers);
    }

    @Override
    public int hashCode() {
        int result = authors != null ? authors.hashCode() : 0;
        result = 31 * result + (books != null ? books.hashCode() : 0);
        result = 31 * result + (publishers != null ? publishers.hashCode() : 0);
        return result;
    }

    private <T> boolean equalsList(List<T> list1, List<T> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        if (!list1.containsAll(list2)) {
            return false;
        }
        if (!list2.containsAll(list1)) {
            return false;
        }
        return true;
    }

    public void print() {
        System.out.println("Authors: " + authors);
        System.out.println("Books: " + books);
        System.out.println("Publishers: " + publishers);
    }
}
