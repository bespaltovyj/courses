package com.epam.training.task7.processing;

import com.epam.training.task7.data.Author;
import com.epam.training.task7.data.Book;
import com.epam.training.task7.data.Data;
import com.epam.training.task7.data.Publisher;

import java.io.*;
import java.util.List;

public class SaveAndLoadBySerialization implements SaveAndLoad {

    @Override
    public void save(Data data, File file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

            outputStream.writeObject(data.getAuthors());
            outputStream.writeObject(data.getBooks());
            outputStream.writeObject(data.getPublishers());
        }
    }

    @Override
    public Data load(File file) throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            List<Author> authors = (List<Author>) objectInputStream.readObject();
            List<Book> books = (List<Book>) objectInputStream.readObject();
            List<Publisher> publishers = (List<Publisher>) objectInputStream.readObject();

            objectInputStream.close();
            Data data = new Data(authors, books, publishers);

            return data;
        }
    }
}
