package com.epam.training.task7;

import java.util.regex.Pattern;

public abstract class Configuration {

    public static final String NULL_IN_FILE = "null";

    public static final char LEFT_BORDER_AROUND_INSTANCE = '{';
    public static final char RIGHT_BORDER_AROUND_INSTANCE = '}';

    public static final char LEFT_BORDER_AROUND_ARRAY = '[';
    public static final char RIGHT_BORDER_AROUND_ARRAY = ']';

    public static final char SEPARATOR_BETWEEN_ARRAY_NAME_AND_ARRAY = ':';
    public static final char SEPARATOR_BETWEEN_ARRAYS = ';';
    public static final char DEFAULT_SEPARATOR_BETWEEN_ELEMENTS_IN_ARRAY = ',';

    // THIS SEPARATOR CANNOT BE A POINT!
    public static final char SEPARATOR_BETWEEN_ELEMENTS_IN_ARRAY = '~';

    public static final char SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT = '=';

    public static final String NAME_OF_ARRAY_OF_AUTHORS = "authors";
    public static final String NAME_OF_ARRAY_OF_BOOKS = "books";
    public static final String NAME_OF_ARRAY_OF_PUBLISHERS = "publishers";


    public static final String NAME_GROUP_WITH_NAME_OF_ARRAY = "nameOfArray";
    public static final String NAME_GROUP_WITH_ARRAY = "array";
    public static final String REG_EXP_FOR_ONE_RECORD = String.format(
            "(?<%s>\\w+?)%c\\%c(?<%s>.*?)\\%c"
            , NAME_GROUP_WITH_NAME_OF_ARRAY
            , Configuration.SEPARATOR_BETWEEN_ARRAY_NAME_AND_ARRAY
            , Configuration.LEFT_BORDER_AROUND_ARRAY
            , NAME_GROUP_WITH_ARRAY
            , Configuration.RIGHT_BORDER_AROUND_ARRAY
    );
    public static final Pattern PATTERN_FOR_ONE_RECORD = Pattern.compile(REG_EXP_FOR_ONE_RECORD);


    public static final String NAME_GROUP_FOR_FIELD_AUTHOR_ID = "authorId";
    public static final String NAME_GROUP_FOR_FIELD_AUTHOR_NAME = "authorName";
    public static final String NAME_GROUP_FOR_FIELD_AUTHOR_DATE_OF_BIRTH = "authorDateOfBirth";
    public static final String NAME_GROUP_FOR_FIELD_AUTHOR_DATE_OF_DEATH = "authorDateOFDeath";
    public static final String NAME_GROUP_FOR_FIELD_AUTHOR_GENDER = "authorGender";
    public static final String REG_EXP_FOR_INSTANCE_AUTHOR = String.format(
            "\\%c(?<%s>.+?)\\Q%c\\E(?<%s>\\w+?)\\Q%c\\E(?<%s>.+?)\\Q%c\\E(?<%s>.+?)\\Q%c\\E(?<%s>\\w+?)%c"
            , LEFT_BORDER_AROUND_INSTANCE
            , NAME_GROUP_FOR_FIELD_AUTHOR_ID
            , SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT
            , NAME_GROUP_FOR_FIELD_AUTHOR_NAME
            , SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT
            , NAME_GROUP_FOR_FIELD_AUTHOR_DATE_OF_BIRTH
            , SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT
            , NAME_GROUP_FOR_FIELD_AUTHOR_DATE_OF_DEATH
            , SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT
            , NAME_GROUP_FOR_FIELD_AUTHOR_GENDER
            , RIGHT_BORDER_AROUND_INSTANCE
    );
    public static final Pattern PATTERN_FOR_INSTANCE_AUTHOR = Pattern.compile(REG_EXP_FOR_INSTANCE_AUTHOR);


    public static final String NAME_GROUP_FOR_FIELD_BOOK_ID = "bookId";
    public static final String NAME_GROUP_FOR_FIELD_BOOK_NAME = "bookName";
    public static final String NAME_GROUP_FOR_FIELD_BOOK_DATE_OF_RELEASE = "bookDateOfRelease";
    public static final String NAME_GROUP_FOR_FIELD_BOOK_AUTHORS_IDS = "authorsIds";
    public static final String REG_EXP_FOR_INSTANCE_BOOK = String.format(
            "\\%c(?<%s>.+?)\\Q%c\\E(?<%s>\\w+?)\\Q%c\\E(?<%s>.+?)\\Q%c\\E(?<%s>\\%c.*?%c)%c"
            , LEFT_BORDER_AROUND_INSTANCE
            , NAME_GROUP_FOR_FIELD_BOOK_ID
            , SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT
            , NAME_GROUP_FOR_FIELD_BOOK_NAME
            , SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT
            , NAME_GROUP_FOR_FIELD_BOOK_DATE_OF_RELEASE
            , SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT
            , NAME_GROUP_FOR_FIELD_BOOK_AUTHORS_IDS
            , LEFT_BORDER_AROUND_ARRAY
            , RIGHT_BORDER_AROUND_ARRAY
            , RIGHT_BORDER_AROUND_INSTANCE
    );
    public static final Pattern PATTERN_FOR_INSTANCE_BOOK = Pattern.compile(REG_EXP_FOR_INSTANCE_BOOK);


    public static final String NAME_GROUP_FOR_FIELD_PUBLISHER_ID = "publisherId";
    public static final String NAME_GROUP_FOR_FIELD_PUBLISHER_BOOKS_IDS = "booksIds";
    public static final String REG_EXP_FOR_INSTANCE_PUBLISHER = String.format(
            "\\%c(?<%s>.+?)\\Q%c\\E(?<%s>\\%c.*?\\%c)\\%c"
            , LEFT_BORDER_AROUND_INSTANCE
            , NAME_GROUP_FOR_FIELD_PUBLISHER_ID
            , SEPARATOR_BETWEEN_FIELDS_IN_ELEMENT
            , NAME_GROUP_FOR_FIELD_PUBLISHER_BOOKS_IDS
            , LEFT_BORDER_AROUND_ARRAY
            , RIGHT_BORDER_AROUND_ARRAY
            , RIGHT_BORDER_AROUND_INSTANCE
    );
    public static final Pattern PATTERN_FOR_INSTANCE_PUBLISHER = Pattern.compile(REG_EXP_FOR_INSTANCE_PUBLISHER);

    public static final String NAME_GROUP_FOR_INNER_ARRAY = "innerArray";
    public static final String REG_EXP_FOR_INNER_ARRAY = String.format(
            "\\%c(?<%s>.*?)\\%c"
            , Configuration.LEFT_BORDER_AROUND_ARRAY
            , NAME_GROUP_FOR_INNER_ARRAY
            , Configuration.RIGHT_BORDER_AROUND_ARRAY
    );
    public static final Pattern PATTERN_FOR_INNER_ARRAY = Pattern.compile(REG_EXP_FOR_INNER_ARRAY);

    public static final Pattern PATTEN_FOR_CHECK_ID = Pattern.compile("-??\\d*");
    public static final Pattern PATTEN_FOR_CHECK_NAME = Pattern.compile("[a-zA-Z]*");
    public static final Pattern PATTEN_FOR_CHECK_DATE = Pattern.compile("([0-9]{1,4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01]))|" + NULL_IN_FILE);
    public static final Pattern PATTEN_FOR_CHECK_GENDER = Pattern.compile("(FE)??MALE");
}
