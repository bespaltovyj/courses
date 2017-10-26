package com.epam.training.task7.record;

import com.epam.training.task7.Configuration;
import com.epam.training.task7.exception.LoadDataException;
import com.epam.training.task7.data.Author;
import com.epam.training.task7.data.Gender;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;

public class AuthorRecord {

    private final String id;
    private String name;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private Gender gender;

    private AuthorRecord(String id, String name, LocalDate dateOfBirth, LocalDate dateOfDeath, Gender gender) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.gender = gender;
    }

    public AuthorRecord(Author author) {
        this.name = author.getName();
        this.dateOfBirth = author.getDateOfBirth();
        this.dateOfDeath = author.getDateOfDeath();
        this.gender = author.getGender();
        this.id = hashCode(author);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return Period.between(this.getDateOfBirth(), this.getDateOfDeath() != null ? dateOfDeath : LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(Configuration.LEFT_BORDER_AROUND_INSTANCE);
        builder.append(id);
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(name);
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(dateOfBirth);
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(dateOfDeath);
        builder.append(Configuration.SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT);
        builder.append(gender);
        builder.append(Configuration.RIGHT_BORDER_AROUND_INSTANCE);
        return builder.toString();
    }

    public static AuthorRecord readAuthorRecordFromString(String authorInString) throws LoadDataException {
        Matcher matcherForInstance = Configuration.PATTERN_FOR_INSTANCE_AUTHOR.matcher(authorInString);
        if (!matcherForInstance.matches()) {
            throw new LoadDataException("String representation of the author is incorrect: " + authorInString);
        }

        String id = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_AUTHOR_ID);
        if (!Configuration.PATTEN_FOR_CHECK_ID.matcher(id).matches()) {
            throw new LoadDataException("String representation id of the author is incorrect: " + id);
        }

        String name = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_AUTHOR_NAME);
        if (!Configuration.PATTEN_FOR_CHECK_NAME.matcher(name).matches()) {
            throw new LoadDataException("String representation name of the author is incorrect: " + name);
        }

        String dateOfBirth = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_AUTHOR_DATE_OF_BIRTH);
        if (!Configuration.PATTEN_FOR_CHECK_DATE.matcher(dateOfBirth).matches()) {
            throw new LoadDataException("String representation dateOfBirth of the author is incorrect: " + dateOfBirth);
        }

        String dateOfDeath = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_AUTHOR_DATE_OF_DEATH);
        if (!Configuration.PATTEN_FOR_CHECK_DATE.matcher(dateOfDeath).matches()) {
            throw new LoadDataException("String representation dateOfDeath of the author is incorrect: " + dateOfDeath);
        }

        String gender = matcherForInstance.group(Configuration.NAME_GROUP_FOR_FIELD_AUTHOR_GENDER);
        if (!Configuration.PATTEN_FOR_CHECK_GENDER.matcher(gender).matches()) {
            throw new LoadDataException("String representation gender of the author is incorrect: " + gender);
        }

        AuthorRecord author = new AuthorRecord(
                id
                , name
                , Configuration.NULL_IN_FILE.equals(dateOfBirth) ? null : LocalDate.parse(dateOfBirth)
                , Configuration.NULL_IN_FILE.equals(dateOfDeath) ? null : LocalDate.parse(dateOfDeath)
                , Gender.valueOf(gender)

        );
        return author;
    }

    public static String hashCode(Author author) {
        return String.valueOf(author.hashCode());
    }

}
