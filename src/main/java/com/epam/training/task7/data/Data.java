package com.epam.training.task7.data;

import com.epam.training.task7.Configuration;
import com.epam.training.task7.data.Author;
import com.epam.training.task7.data.Book;
import com.epam.training.task7.data.Publisher;
import com.epam.training.task7.record.AuthorRecord;
import com.epam.training.task7.record.BookRecord;
import com.epam.training.task7.record.PublisherRecord;

import java.util.List;

public class Data {

    private List<Author> authors;
    private List<Book> books;
    private List<Publisher> publishers;

    public Data(List<Author> authors, List<Book> books, List<Publisher> publishers) {
        this.authors = authors;
        this.books = books;
        this.publishers = publishers;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(Configuration.LEFT_BORDER_AROUND_INSTANCE);
        builder.append(authors);
        builder.append(Configuration.SEPARATOR_BETWEEN_ARRAYS);
        builder.append('\n');
        builder.append(books);
        builder.append(Configuration.SEPARATOR_BETWEEN_ARRAYS);
        builder.append('\n');
        builder.append(publishers);
        builder.append(Configuration.RIGHT_BORDER_AROUND_INSTANCE);
        return builder.toString();
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
}
