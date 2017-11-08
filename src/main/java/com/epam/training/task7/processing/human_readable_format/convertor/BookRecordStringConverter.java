package com.epam.training.task7.processing.human_readable_format.convertor;

import com.epam.training.task7.Configuration;
import com.epam.training.task7.exception.LoadDataException;
import com.epam.training.task7.record.BookRecord;
import com.epam.training.task7.record.Record;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class BookRecordStringConverter implements RecordStringConverter {
    @Override
    public String getInstanceAsString(Record record) {
        BookRecord bookRecord = (BookRecord) record;
        StringBuilder builder = new StringBuilder();
        builder.append(Configuration.LEFT_BORDER_AROUND_INSTANCE);
        builder.append(bookRecord.getId());
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(bookRecord.getName());
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(bookRecord.getDateOfRelease());
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(bookRecord.getAuthorsIds());
        builder.append(Configuration.RIGHT_BORDER_AROUND_INSTANCE);
        return builder.toString();
    }

    public static BookRecord getInstanceFromString(String bookInString) throws LoadDataException {
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

        String dateOfReleaseAsString = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_BOOK_DATE_OF_RELEASE);
        if (!Configuration.PATTEN_FOR_CHECK_DATE.matcher(dateOfReleaseAsString).matches()) {
            throw new LoadDataException("String representation dateOfRelease of the book is incorrect: " + dateOfReleaseAsString);
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

        LocalDate dateOfRelease = Configuration.NULL_IN_FILE.equals(dateOfReleaseAsString) ? null : LocalDate.parse(dateOfReleaseAsString);
        return new BookRecord(id, name, dateOfRelease, authorsIds);
    }
}
