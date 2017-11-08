package com.epam.training.task8.sax.buffer;

import com.epam.training.task7.record.BookRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookRecordBuffer {

    private String id;
    private String name;
    private LocalDate dateOfRelease;
    private List<String> authorsIds;

    public BookRecordBuffer() {
        authorsIds = new ArrayList<>();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfRelease(LocalDate dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    public List<String> getAuthorsIds() {
        return authorsIds;
    }

    public BookRecord getBookRecord() {
        return new BookRecord(id, name, dateOfRelease, authorsIds);
    }

    public void flushFields() {
        id = null;
        name = null;
        dateOfRelease = null;
        authorsIds = new ArrayList<>();
    }
}
