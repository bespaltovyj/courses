package com.epam.training.task8.sax.buffer;

import com.epam.training.task7.record.BookRecord;
import com.epam.training.task7.record.PublisherRecord;

import java.util.ArrayList;
import java.util.List;

public class PublisherRecordBuffer {
    private String id;
    private List<String> bookIds;

    public PublisherRecordBuffer() {
        bookIds = new ArrayList<>();
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getBookIds() {
        return bookIds;
    }

    public PublisherRecord getPublisherRecord() {
        return new PublisherRecord(id, bookIds);
    }

    public void flushFields() {
        id = null;
        bookIds = new ArrayList<>();
    }
}
