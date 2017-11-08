package com.epam.training.task9.json;

import com.epam.training.task7.data.Data;
import com.epam.training.task7.processing.Serializer;
import com.epam.training.task7.processing.Transformation;
import com.epam.training.task7.record.DataRecord;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class SerializerIntoJSON implements Serializer {

    @Override
    public void serialize(Data data, File file) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DataRecord dataRecord = Transformation.transformDataToRecord(data);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, dataRecord);
    }
}
