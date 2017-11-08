package com.epam.training.task7.processing.human_readable_format;

import com.epam.training.task7.Configuration;
import com.epam.training.task7.data.Data;
import com.epam.training.task7.exception.LoadDataException;
import com.epam.training.task7.processing.Deserializer;
import com.epam.training.task7.processing.Transformation;
import com.epam.training.task7.processing.human_readable_format.convertor.AuthorRecordStringConverter;
import com.epam.training.task7.processing.human_readable_format.convertor.BookRecordStringConverter;
import com.epam.training.task7.processing.human_readable_format.convertor.PublisherRecordStringConverter;
import com.epam.training.task7.record.AuthorRecord;
import com.epam.training.task7.record.BookRecord;
import com.epam.training.task7.record.DataRecord;
import com.epam.training.task7.record.PublisherRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class DeserializerFromHumanReadableFormat implements Deserializer {

    @Override
    public Data deserialize(File file) throws LoadDataException {
        String allLinesFromFileInOneLine = readAllLinesFromFileInOneString(file);

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
            String nameArray = matcherForNameArrayAndArray.group(Configuration.NAME_GROUP_WITH_NAME_OF_ARRAY);
            for (String instance : instances) {
                switch (nameArray) {
                    case Configuration.NAME_OF_ARRAY_OF_AUTHORS:
                        AuthorRecord author = AuthorRecordStringConverter.getInstanceFromString(instance);
                        authors.add(author);
                        break;
                    case Configuration.NAME_OF_ARRAY_OF_BOOKS:
                        BookRecord book = BookRecordStringConverter.getInstanceFromString(instance);
                        books.add(book);
                        break;
                    case Configuration.NAME_OF_ARRAY_OF_PUBLISHERS:
                        PublisherRecord publisher = PublisherRecordStringConverter.getInstanceFromString(instance);
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

    private String readAllLinesFromFileInOneString(File file) throws LoadDataException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] allLinesFromFileInByteFormat = new byte[fileInputStream.available()];
            final int countReadBytes = fileInputStream.read(allLinesFromFileInByteFormat);
            if (countReadBytes < 0) {
                return null;
            }
            String allLinesFromFileInOneString = new String(allLinesFromFileInByteFormat);
            // removing spaces and line breaking
            allLinesFromFileInOneString = allLinesFromFileInOneString.replaceAll("\\s*\\n*", "");
            return allLinesFromFileInOneString;
        } catch (FileNotFoundException e) {
            throw new LoadDataException("File " + file + " does not exist", e);
        } catch (IOException e) {
            throw new LoadDataException("IOException in file " + file, e);
        }
    }
}
