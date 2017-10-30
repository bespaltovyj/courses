package com.epam.training.task7.processing;

import com.epam.training.task7.data.Data;

import java.io.*;

public class SerializerIntoBytes implements Serializer {

    @Override
    public void serialize(Data data, File file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(data);
        }
    }

}
