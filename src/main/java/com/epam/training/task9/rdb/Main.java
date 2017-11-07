package com.epam.training.task9.rdb;


import java.io.File;
import java.net.URL;
import java.util.Objects;


public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length > 0 && Objects.equals("createDB", args[0])) {
            URL url = Thread.currentThread().getContextClassLoader().getResource("rdb.properties");
            final File fileProperties = new File(url.getFile());
            Util.executeSQLScript(fileProperties, "createTables.sql");
            Util.fillTablesFromXMLFile(fileProperties, "test.xml");
        }
    }

}
