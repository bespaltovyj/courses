package com.epam.training.task7;


import com.epam.training.task7.data.*;
import com.epam.training.task7.processing.SaveAndLoad;
import com.epam.training.task7.processing.SaveAndLoadByHumanReadableFormat;
import com.epam.training.task7.processing.SaveAndLoadBySerialization;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class Test {

    public static void test() {
        List<Author> authors = new ArrayList<>();
        List<Book> books = new ArrayList<>();
        List<Publisher> publishers = new ArrayList<>();

        Test.fillAuthorsAndBooksAndPublishersList(authors, books, publishers);
        Data data = new Data(authors, books, publishers);


        try {
            final File humanReadableFile = new File("./src/com/epam/training/task7/humanReadable.txt");

            SaveAndLoad saveAndLoadByHumanReadableFormat = new SaveAndLoadByHumanReadableFormat();
            saveAndLoadByHumanReadableFormat.save(data, humanReadableFile);
            Data loadDataByHumanReadable = saveAndLoadByHumanReadableFormat.load(humanReadableFile);

            System.out.print("Result of comparison of old data and of load data by human-readable format: ");
            System.out.println(Objects.equals(data, loadDataByHumanReadable));

            final File serializationFile = new File("./src/com/epam/training/task7/serializationFile");
            SaveAndLoad saveAndLoadBySerialization = new SaveAndLoadBySerialization();
            saveAndLoadBySerialization.save(data, serializationFile);
            Data loadDataBySerialization = saveAndLoadBySerialization.load(serializationFile);

            System.out.print("Result of comparison of old data and of load data by serialization format: ");
            System.out.println(Objects.equals(data, loadDataBySerialization));


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    public static void fillAuthorsAndBooksAndPublishersList(List<Author> authors, List<Book> books, List<Publisher> publishers) {
        Author authorBridge = new Author(
                "Bridge"
                , LocalDate.of(1889, Month.SEPTEMBER, 11)
                , LocalDate.of(1974, Month.MAY, 9)
                , Gender.FEMALE
        );
        authors.add(authorBridge);

        Author authorAdams = new Author(
                "Adams"
                , LocalDate.of(1920, Month.MAY, 9)
                , LocalDate.of(2016, Month.DECEMBER, 24)
                , Gender.MALE
        );
        authors.add(authorAdams);

        Author authorMay = new Author(
                "May"
                , LocalDate.of(1950, Month.JUNE, 17)
                , null
                , Gender.MALE
        );
        authors.add(authorMay);

        Author authorBoosh = new Author(
                "Boosh"
                , LocalDate.of(1970, Month.DECEMBER, 19)
                , null
                , Gender.MALE
        );
        authors.add(authorBoosh);

        Author authorQwers = new Author(
                "Qwers"
                , LocalDate.of(1990, Month.MAY, 11)
                , null
                , Gender.FEMALE
        );
        authors.add(authorQwers);

        Author authorGroh = new Author(
                "Groh"
                , LocalDate.of(1950, Month.MAY, 29)
                , null
                , Gender.MALE
        );
        authors.add(authorGroh);

        Book bookBloch = new Book(
                "Bloch"
                , LocalDate.of(1940, Month.AUGUST, 17)
                , new ArrayList<>(Arrays.asList(authorBridge, authorAdams))
        );
        books.add(bookBloch);

        Book bookHouse = new Book(
                "House"
                , LocalDate.of(1950, Month.JUNE, 15)
                , new ArrayList<>(Arrays.asList(authorBridge))
        );
        books.add(bookHouse);

        Book bookTable = new Book(
                "Table"
                , LocalDate.of(1955, Month.SEPTEMBER, 1)
                , new ArrayList<>(Arrays.asList(authorAdams, authorMay))
        );
        books.add(bookTable);

        Book bookApple = new Book(
                "Apple"
                , LocalDate.of(2000, Month.AUGUST, 17)
                , new ArrayList<>(Arrays.asList(authorQwers, authorGroh))
        );
        books.add(bookApple);

        Book bookCake = new Book(
                "Cake"
                , LocalDate.of(2005, Month.APRIL, 1)
                , new ArrayList<>(Arrays.asList(authorBoosh))
        );
        books.add(bookCake);

        Publisher piter = new Publisher(new ArrayList<>(Arrays.asList(bookCake, bookApple)));
        Publisher eksmo = new Publisher(new ArrayList<>(Arrays.asList(bookHouse, bookApple, bookBloch)));
        Publisher luck = new Publisher(new ArrayList<>(Arrays.asList(bookTable)));

        publishers.add(piter);
        publishers.add(eksmo);
        publishers.add(luck);
    }
}
