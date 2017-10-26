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
            outputStream.writeObject(data);
        }
    }

    @Override
    public Data load(File file) throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Data data = (Data) objectInputStream.readObject();
            return data;
        }
    }
}
