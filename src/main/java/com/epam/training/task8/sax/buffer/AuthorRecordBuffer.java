package com.epam.training.task8.sax.buffer;

import com.epam.training.task7.data.Gender;
import com.epam.training.task7.record.AuthorRecord;

import java.time.LocalDate;

public class AuthorRecordBuffer {

    private String id;
    private String name;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private Gender gender;

    public AuthorRecordBuffer() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public AuthorRecord getAuthorRecord() {
        return new AuthorRecord(id, name, dateOfBirth, dateOfDeath, gender);
    }

    public void flushFields() {
        id = null;
        name = null;
        dateOfBirth = null;
        dateOfDeath = null;
        gender = null;
    }
}
