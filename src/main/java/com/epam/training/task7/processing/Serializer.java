package com.epam.training.task7.processing;

import com.epam.training.task7.data.Data;

import java.io.File;

public interface Serializer {

    void serialize(Data data, File file) throws Exception;

}
