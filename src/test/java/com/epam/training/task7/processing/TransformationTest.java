package com.epam.training.task7.processing;

import com.epam.training.task7.data.Gender;
import com.epam.training.task7.exception.ValidateDataException;
import com.epam.training.task7.record.AuthorRecord;
import com.epam.training.task7.record.BookRecord;
import com.epam.training.task7.record.PublisherRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TransformationTest {
    @Test
    @DisplayName("check dateOfBirth and dateOfDeath in author")
    void validateFieldsAndCreateAuthor() {
        LocalDate dateOfBirth = LocalDate.of(2017, 12, 12);
        LocalDate dateOfDeath = LocalDate.of(2067, 12, 11);
        AuthorRecord authorRecord = new AuthorRecord("1", "name", dateOfDeath, dateOfBirth, Gender.MALE);
        Executable executable = () -> Transformation.validateFieldsAndCreateAuthor(authorRecord);
        assertThrows(ValidateDataException.class, executable);
    }

    @Test
    @DisplayName("check empty list authors in book")
    void checkCreateBookOnEmptyListAuhtors() {
        BookRecord bookRecord = new BookRecord("1", "name", null, new ArrayList<>());
        Executable executable = () -> Transformation.validateFieldsAndCreateBook(bookRecord, new HashMap<>());
        assertThrows(ValidateDataException.class, executable);
    }

    @Test
    @DisplayName("check for the existence of the author in book")
    void checkCreateBookOnExistAuthor() {
        BookRecord bookRecord = new BookRecord("1", "name", null, new ArrayList<>(Arrays.asList("2")));
        Executable executable = () -> Transformation.validateFieldsAndCreateBook(bookRecord, new HashMap<>());
        assertThrows(ValidateDataException.class, executable);
    }


    @Test
    @DisplayName("check for the existence of the book in publisher")
    void validateFieldsAndCreatePublishers() {
        PublisherRecord publisherRecord = new PublisherRecord("1", new ArrayList<>(Arrays.asList("2")));
        Executable executable = () -> Transformation.validateFieldsAndCreatePublisher(publisherRecord, new HashMap<>());
        assertThrows(ValidateDataException.class, executable);
    }

}