package com.epam.training.task7.processing.human_readable_format.convertor;

import com.epam.training.task7.Configuration;
import com.epam.training.task7.exception.LoadDataException;
import com.epam.training.task7.record.PublisherRecord;
import com.epam.training.task7.record.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class PublisherRecordStringConverter implements RecordStringConverter {

    @Override
    public String getInstanceAsString(Record record) {
        PublisherRecord publisherRecord = (PublisherRecord) record;
        StringBuilder builder = new StringBuilder();
        builder.append(Configuration.LEFT_BORDER_AROUND_INSTANCE);
        builder.append(publisherRecord.getId());
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(publisherRecord.getBookIds());
        builder.append(Configuration.RIGHT_BORDER_AROUND_INSTANCE);
        return builder.toString();
    }

    public static PublisherRecord getInstanceFromString(String publisherInString) throws LoadDataException {
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

        return new PublisherRecord(id, booksIds);
    }
}
