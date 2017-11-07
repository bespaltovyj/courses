package com.epam.training.task7.processing;

import com.epam.training.task7.data.Data;
import com.epam.training.task7.exception.LoadDataException;

import java.io.File;

public interface Deserializer {
    Data deserialize(File file) throws LoadDataException;
}
