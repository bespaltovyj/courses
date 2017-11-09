package com.epam.training.task9.rdb;


import com.epam.training.Log;

import java.util.Objects;


public class Main {

    public static void main(String[] args){
        try {
            if (args.length > 0 && Objects.equals("createDB", args[0])) {
                Util.dropTables("dropTables.sql");
                Util.createTablesAndFillFromXML("createTables.sql", "test.xml");
            }
        } catch (Exception e) {
            Log.log.error(e.getMessage());
        }
    }

}
