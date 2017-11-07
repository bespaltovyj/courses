package com.epam.training.task7.processing;

import com.epam.training.task7.data.Data;
import com.epam.training.task7.exception.LoadDataException;

import java.io.*;

public class DeserializerFromBytes implements Deserializer {

    @Override
    public Data deserialize(File file) throws LoadDataException {
        try (FileInputStream fileInputStream = new FileInputStream(file)
             ; ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (Data) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new LoadDataException("File " + file + " does not exist", e);
        } catch (IOException e) {
            throw new LoadDataException("IOException thrown when processing file " + file, e);
        } catch (ClassNotFoundException e) {
            throw new LoadDataException("ClassNotFoundException", e);
        }
    }
}

