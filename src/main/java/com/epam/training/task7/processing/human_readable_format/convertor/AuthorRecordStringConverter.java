package com.epam.training.task7.processing.human_readable_format.convertor;

import com.epam.training.task7.Configuration;
import com.epam.training.task7.data.Gender;
import com.epam.training.task7.exception.LoadDataException;
import com.epam.training.task7.record.AuthorRecord;
import com.epam.training.task7.record.Record;

import java.time.LocalDate;
import java.util.regex.Matcher;

public class AuthorRecordStringConverter implements RecordStringConverter {

    public String getInstanceAsString(Record record) {
        AuthorRecord authorRecord = (AuthorRecord) record;
        StringBuilder builder = new StringBuilder();
        builder.append(Configuration.LEFT_BORDER_AROUND_INSTANCE);
        builder.append(authorRecord.getId());
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(authorRecord.getName());
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(authorRecord.getDateOfBirth());
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(authorRecord.getDateOfDeath());
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(authorRecord.getGender());
        builder.append(Configuration.RIGHT_BORDER_AROUND_INSTANCE);
        return builder.toString();
    }

    public static AuthorRecord getInstanceFromString(String instanceInString) throws LoadDataException {
        Matcher matcherForInstance = Configuration.PATTERN_FOR_INSTANCE_AUTHOR.matcher(instanceInString);
        if (!matcherForInstance.matches()) {
            throw new LoadDataException("String representation of the author is incorrect: " + instanceInString);
        }

        String id = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_AUTHOR_ID);
        if (!Configuration.PATTEN_FOR_CHECK_ID.matcher(id).matches()) {
            throw new LoadDataException("String representation id of the author is incorrect: " + id);
        }

        String name = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_AUTHOR_NAME);
        if (!Configuration.PATTEN_FOR_CHECK_NAME.matcher(name).matches()) {
            throw new LoadDataException("String representation name of the author is incorrect: " + name);
        }

        String dateOfBirthAsString = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_AUTHOR_DATE_OF_BIRTH);
        if (!Configuration.PATTEN_FOR_CHECK_DATE.matcher(dateOfBirthAsString).matches()) {
            throw new LoadDataException("String representation dateOfBirth of the author is incorrect: " + dateOfBirthAsString);
        }

        String dateOfDeathAsString = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_AUTHOR_DATE_OF_DEATH);
        if (!Configuration.PATTEN_FOR_CHECK_DATE.matcher(dateOfDeathAsString).matches()) {
            throw new LoadDataException("String representation dateOfDeath of the author is incorrect: " + dateOfDeathAsString);
        }

        String gender = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_AUTHOR_GENDER);
        if (!Configuration.PATTEN_FOR_CHECK_GENDER.matcher(gender).matches()) {
            throw new LoadDataException("String representation gender of the author is incorrect: " + gender);
        }

        LocalDate dateOfBirth = Configuration.NULL_IN_FILE.equals(dateOfBirthAsString) ? null : LocalDate.parse(dateOfBirthAsString);
        LocalDate dateOfDeath = Configuration.NULL_IN_FILE.equals(dateOfDeathAsString) ? null : LocalDate.parse(dateOfDeathAsString);

        return new AuthorRecord(id, name, dateOfBirth, dateOfDeath, Gender.valueOf(gender));
    }
}
