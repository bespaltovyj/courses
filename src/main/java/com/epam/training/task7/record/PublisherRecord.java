package com.epam.training.task7.record;

import com.epam.training.task7.Configuration;
import com.epam.training.task7.exception.LoadDataException;
import com.epam.training.task7.data.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class PublisherRecord {

    private final String id;
    private List<String> bookIds;

    private PublisherRecord(String id, List<String> bookIds) {
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(Configuration.LEFT_BORDER_AROUND_INSTANCE);
        builder.append(id);
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(bookIds);
        builder.append(Configuration.RIGHT_BORDER_AROUND_INSTANCE);
        return builder.toString();
    }

    public static PublisherRecord readPublisherRecordFromString(String publisherInString) throws LoadDataException {
        Matcher matcherForInstance = Configuration.PATTERN_FOR_INSTANCE_PUBLISHER.matcher(publisherInString);
        if (!matcherForInstance.matches()) {
            throw new LoadDataException("String representation of the publisher is incorrect: " + publisherInString);
        }

        String id = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_PUBLISHER_ID);
        if (!Configuration.PATTEN_FOR_CHECK_ID.matcher(id).matches()) {
            throw new LoadDataException("String representation id of the publisher is incorrect: " + id);
        }

        String arrayBooksIds = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_PUBLISHER_BOOKS_IDS);
        Matcher matcherForInnerArrayInPublisher = Configuration.PATTERN_FOR_INNER_ARRAY.matcher(arrayBooksIds);
        if (!matcherForInnerArrayInPublisher.matches()) {
            throw new LoadDataException("String representation array book id: " + arrayBooksIds + " in the book" + id + " is incorrect: ");
        }

        String[] arrBooksId =
                matcherForInnerArrayInPublisher.group(Configuration.NAME_GROUP_FOR_INNER_ARRAY)
                        .split(String.valueOf(Configuration.DEFAULT_SEPARATOR_BETWEEN_ELEMENTS_IN_ARRAY));
        List<String> booksIds = new ArrayList<>();
        for (String bookId : arrBooksId) {
            if (!Configuration.PATTEN_FOR_CHECK_ID.matcher(bookId).matches()) {
                throw new LoadDataException("String representation book id: " + bookId + " in the publisher" + id + " is incorrect: ");
            }
            booksIds.add(bookId);
        }

        PublisherRecord publisher = new PublisherRecord(
                id
                , booksIds

        );
        return publisher;
    }

    public static String hashCode(Publisher publisher) {
        return String.valueOf(publisher.hashCode());
    }
}
