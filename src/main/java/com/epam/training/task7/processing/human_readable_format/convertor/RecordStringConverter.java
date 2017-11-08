package com.epam.training.task7.processing.human_readable_format.convertor;

import com.epam.training.task7.record.Record;

public interface RecordStringConverter {
    String getInstanceAsString(Record record);
}
