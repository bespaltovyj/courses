package com.epam.training.task7.processing;

import com.epam.training.task7.data.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class DeserializerFromBytes implements Deserializer {

    @Override
    public Data deserialize(File file) throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Data data = (Data) objectInputStream.readObject();
            return data;
        }
    }
}

