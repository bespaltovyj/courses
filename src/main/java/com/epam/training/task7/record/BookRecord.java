package com.epam.training.task7.record;

import com.epam.training.task7.Configuration;
import com.epam.training.task7.exception.LoadDataException;
import com.epam.training.task7.data.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class BookRecord {

    private final String id;
    private String name;
    private LocalDate dateOfRelease;
    private List<String> authorsIds;

    private BookRecord(String id, String name, LocalDate dateOfRelease, List<String> authorsIds) {
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(Configuration.LEFT_BORDER_AROUND_INSTANCE);
        builder.append(id);
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(name);
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(dateOfRelease);
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(authorsIds);
        builder.append(Configuration.RIGHT_BORDER_AROUND_INSTANCE);
        return builder.toString();
    }

    public static BookRecord readBookRecordFromString(String bookInString) throws LoadDataException {
        Matcher matcherForInstance = Configuration.PATTERN_FOR_INSTANCE_BOOK.matcher(bookInString);
        if (!matcherForInstance.matches()) {
            throw new LoadDataException("String representation of the book is incorrect: " + bookInString);
        }

        String id = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_BOOK_ID);
        if (!Configuration.PATTEN_FOR_CHECK_ID.matcher(id).matches()) {
            throw new LoadDataException("String representation id of the book is incorrect: " + id);
        }

        String name = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_BOOK_NAME);
        if (!Configuration.PATTEN_FOR_CHECK_NAME.matcher(name).matches()) {
            throw new LoadDataException("String representation name of the book is incorrect: " + name);
        }

        String dateOfRelease = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_BOOK_DATE_OF_RELEASE);
        if (!Configuration.PATTEN_FOR_CHECK_DATE.matcher(dateOfRelease).matches()) {
            throw new LoadDataException("String representation dateOfRelease of the book is incorrect: " + dateOfRelease);
        }

        String arrayAuthorsIds = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_BOOK_AUTHORS_IDS);
        Matcher matcherForInnerArray = Configuration.PATTERN_FOR_INNER_ARRAY.matcher(arrayAuthorsIds);
        if (!matcherForInnerArray.matches()) {
            throw new LoadDataException("String representation array author id: " + arrayAuthorsIds + " in the book" + id + " is incorrect: ");
        }

        String[] arrAuthorsId =
                matcherForInnerArray.group(Configuration.NAME_GROUP_FOR_INNER_ARRAY)
                        .split(String.valueOf(Configuration.DEFAULT_SEPARATOR_BETWEEN_ELEMENTS_IN_ARRAY));
        List<String> authorsIds = new ArrayList<>();
        for (String authorId : arrAuthorsId) {
            if (!Configuration.PATTEN_FOR_CHECK_ID.matcher(authorId).matches()) {
                throw new LoadDataException("String representation author id: " + authorId + " in the book" + id + " is incorrect: ");
            }
            authorsIds.add(authorId);
        }

        BookRecord book = new BookRecord(
                id
                , name
                , Configuration.NULL_IN_FILE.equals(dateOfRelease) ? null : LocalDate.parse(dateOfRelease)
                , authorsIds

        );
        return book;
    }

    public static String hashCode(Book book) {
        return String.valueOf(book.hashCode());
    }
}
