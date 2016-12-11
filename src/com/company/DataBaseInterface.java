package com.company;

/**
 * Created by savch on 10.12.2016.
 * Yoj
 */
interface DataBaseInterface {

    void connectDB();

    void insertIntoTableDB(String model, String engine, int count);

    void insertIntoTableDB(String name, int id_car, int count);

    void resetDB();

    void selectRecordTableDB();

    void endConDB();

    void dropTableDB(String table1, String table2);

    void createTableDB();
}
