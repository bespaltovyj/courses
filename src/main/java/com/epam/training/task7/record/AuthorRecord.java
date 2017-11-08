package com.epam.training.task7.record;

import com.epam.training.task7.Configuration;
import com.epam.training.task7.data.Author;
import com.epam.training.task7.data.Gender;
import com.epam.training.task7.exception.LoadDataException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;

public class AuthorRecord extends Record {

    private final String id;
    private String name;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private Gender gender;

    public AuthorRecord(String id, String name, LocalDate dateOfBirth, LocalDate dateOfDeath, Gender gender) {
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

    public static String hashCode(Author author) {
        return String.valueOf(author.hashCode());
    }

}
