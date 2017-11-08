package com.epam.training.task7.processing;

import com.epam.training.task7.Configuration;
import com.epam.training.task7.data.Data;
import com.epam.training.task7.record.DataRecord;
import com.epam.training.task7.record.Record;
import com.epam.training.task7.record.StringRepresentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * This class implements reading and writing List in human-readable format to a file.
 * Storage structure:
 * nameArray<separator between name and array><left border array><instance1><separator between instance><instance2>..<right border array><separator between arrays>
 * Example:
 * authors:[{instance1}~{instance2}...{instance3}];
 * books:[{instance1}~{instance1}];
 * Each instance is written as <left border instance><field1><separator between fields><field2>...<field10><right border instance>
 * Each instance have as id. The order of record fields are important.
 * Example:
 * {-899201230=Boosh=1970-12-19=null=MALE}
 * Spaces and line breaking are ignored when parsing files
 * All parameters specified in the Configuration file.
 */
public class SerializerIntoHumanReadableFormat implements Serializer {

    @Override
    public void serialize(Data data, File file) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(file)) {

            DataRecord dataRecord = Transformation.transformDataToRecord(data);

            writeArray(writer, Configuration.NAME_OF_ARRAY_OF_AUTHORS, dataRecord.getAuthors());
            writeArray(writer, Configuration.NAME_OF_ARRAY_OF_BOOKS, dataRecord.getBooks());
            writeArray(writer, Configuration.NAME_OF_ARRAY_OF_PUBLISHERS, dataRecord.getPublishers());
        }
    }

    private void writeArray(PrintWriter writer, String nameArray, List<? extends Record> arrays) {
        writer.print(nameArray);
        writer.print(Configuration.SEPARATOR_BETWEEN_ARRAY_NAME_AND_ARRAY);
        writer.print(Configuration.LEFT_BORDER_AROUND_ARRAY);
        String arrayInString = arrays.stream()
                .map(StringRepresentation::getInstanceAsString)
                .reduce((x, y) -> x + Configuration.SEPARATOR_BETWEEN_ELEMENTS_IN_ARRAY + "\n" + y)
                .get();
        writer.print(arrayInString);
        writer.print(Configuration.RIGHT_BORDER_AROUND_ARRAY);
        writer.println(Configuration.SEPARATOR_BETWEEN_ARRAYS);
        writer.println();
    }



}
