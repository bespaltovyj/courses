package com.epam.training.task7.processing;

import com.epam.training.task7.data.Data;

import java.io.File;

public interface SaveAndLoad {
    void save(Data data, File file) throws Exception;

    Data load(File file) throws Exception;
}
