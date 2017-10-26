package com.epam.training.task7.processing;

import com.epam.training.task7.data.Data;

import java.io.*;

public class SerializerIntoBytes implements Serializer {

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
