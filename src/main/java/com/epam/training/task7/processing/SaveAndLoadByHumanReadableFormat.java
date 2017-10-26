package com.epam.training.task7.processing;

import com.epam.training.task7.Configuration;
import com.epam.training.task7.data.*;
import com.epam.training.task7.exception.LoadDataException;
import com.epam.training.task7.record.AuthorRecord;
import com.epam.training.task7.record.BookRecord;
import com.epam.training.task7.record.DataRecord;
import com.epam.training.task7.record.PublisherRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

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
public class SaveAndLoadByHumanReadableFormat implements SaveAndLoad {

    @Override
    public void save(Data data, File file) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(file)) {

            DataRecord dataRecord = Transformation.transformDataToRecord(data);

            writeArray(writer, Configuration.NAME_OF_ARRAY_OF_AUTHORS, dataRecord.getAuthors());
            writeArray(writer, Configuration.NAME_OF_ARRAY_OF_BOOKS, dataRecord.getBooks());
            writeArray(writer, Configuration.NAME_OF_ARRAY_OF_PUBLISHERS, dataRecord.getPublishers());
        }
    }

    private <T> void writeArray(PrintWriter writer, String nameArray, List<T> arrays) {
        writer.print(nameArray);
        writer.print(Configuration.SEPARATOR_BETWEEN_ARRAY_NAME_AND_ARRAY);
        writer.print(Configuration.LEFT_BORDER_AROUND_ARRAY);
        String arrayInString = arrays.stream()
                .map(Object::toString)
                .reduce((x, y) -> x + Configuration.SEPARATOR_BETWEEN_ELEMENTS_IN_ARRAY + "\n" + y)
                .get();
        writer.print(arrayInString);
        writer.print(Configuration.RIGHT_BORDER_AROUND_ARRAY);
        writer.println(Configuration.SEPARATOR_BETWEEN_ARRAYS);
        writer.println();
    }

    @Override
    public Data load(File file) throws LoadDataException, IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] allLinesFromFileInByteFormat = new byte[fileInputStream.available()];
            final int countReadBytes = fileInputStream.read(allLinesFromFileInByteFormat);
            if (countReadBytes < 0) {
                return null;
            }
            String allLinesFromFileInOneLine = new String(allLinesFromFileInByteFormat);
            // removing spaces and line breaking
            allLinesFromFileInOneLine = allLinesFromFileInOneLine.replaceAll("\\s*\\n*", "");

            List<AuthorRecord> authors = new ArrayList<>();
            List<BookRecord> books = new ArrayList<>();
            List<PublisherRecord> publishers = new ArrayList<>();

            for (String arrayInStringRepresentation : allLinesFromFileInOneLine.split(String.valueOf(Configuration.SEPARATOR_BETWEEN_ARRAYS))) {
                Matcher matcherForNameArrayAndArray = Configuration.PATTERN_FOR_ONE_RECORD.matcher(arrayInStringRepresentation);
                if (!matcherForNameArrayAndArray.matches()) {
                    throw new LoadDataException("Array " + arrayInStringRepresentation + "incorrect recorded");
                }
                String arrayInstances = matcherForNameArrayAndArray.group(Configuration.NAME_GROUP_WITH_ARRAY);
                String[] instances = arrayInstances.split(String.valueOf(Configuration.SEPARATOR_BETWEEN_ELEMENTS_IN_ARRAY));
                for (String instance : instances) {
                    String nameArray = matcherForNameArrayAndArray.group(Configuration.NAME_GROUP_WITH_NAME_OF_ARRAY);
                    switch (nameArray) {
                        case Configuration.NAME_OF_ARRAY_OF_AUTHORS:
                            AuthorRecord author = AuthorRecord.readAuthorRecordFromString(instance);
                            authors.add(author);
                            break;
                        case Configuration.NAME_OF_ARRAY_OF_BOOKS:
                            BookRecord book = BookRecord.readBookRecordFromString(instance);
                            books.add(book);
                            break;
                        case Configuration.NAME_OF_ARRAY_OF_PUBLISHERS:
                            PublisherRecord publisher = PublisherRecord.readPublisherRecordFromString(instance);
                            publishers.add(publisher);
                            break;
                        default:
                            throw new LoadDataException("Array " + arrayInstances + " has incorrect name" + nameArray);
                    }
                }
            }

            DataRecord dataRecord = new DataRecord(authors, books, publishers);

            return Transformation.transformRecordToData(dataRecord);
        }
    }

}
