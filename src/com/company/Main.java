package com.company;
import java.util.Random;
import java.util.Date;

public class Main {
    private static String models[] = {"KIA", "Mersedes", "Ferarri", "Honda", "Hundai", "Tesla",
            "Lada", "Tavria"};
    private static String engines[] = {"v10", "v12", "v6", "v4", "govno"};

    private static void insertData(mysqlDB ob, String tableName, int n){
        int idx, idx1, id_car;//, countCar = 1;
        String randomModels, randomEngines, name;
        Random rn = new Random();

        switch (tableName) {
            case "car":
                for(int i=1; i <= n; i++) {
                    idx = new Random().nextInt(models.length);
                    idx1 = new Random().nextInt(engines.length);
                    randomModels = (models[idx]);
                    randomEngines = (engines[idx1]);
                    ob.insertIntoTableDB(randomModels, randomEngines, i);
                    //countCar++;
                }
                break;
            case "master":
                for(int i=1; i <= n/2; i++) {
                    name = "Name" + String.valueOf(i);
                    id_car = rn.nextInt(n);
                    ob.insertIntoTableDB(name, id_car, i);
                }
                break;
            default:
                throw new IllegalArgumentException(tableName);
        }
    }

    private static double checkTime(Date ob1, Date ob2){
        long msElapsedTime;
        double resTime;
        msElapsedTime = ob2.getTime() - ob1.getTime();
        Long longObject = new Long(String.valueOf(msElapsedTime));
        return longObject.doubleValue() * 0.001;
    }

    public static void main(String[] args) {
        double time1, time2, time3;

       // mysqlDB obTestDB = new mysqlDB("TestDB", "savchukndr", "savchukao22");
	    pgsqlDB obTestPGDB = new pgsqlDB("postgres", "savchukndr", "savchukao22");

	    //--------MySQL------------
	    /*obTestDB.connectDB();
        //obTestDB.resetDB();

        obTestDB.dropTableDB("master", "car");

        obTestDB.createTableDB();

        Date startDate = new Date();
        insertData(obTestDB, "car", 100);
        Date endDate = new Date();

        time1 = checkTime(startDate, endDate);

        Date startDate1 = new Date();
        insertData(obTestDB, "master", 100);
        Date endDate1 = new Date();

        time2 = checkTime(startDate1, endDate1);

        Date startDate2 = new Date();
            obTestDB.selectRecordTableDB();
        Date endDate2 = new Date();

        time3 = checkTime(startDate2, endDate2);

        System.out.println("Insert into car time: " + time1 + " sec");
        System.out.println("Insert into master time: " + time2 + " sec");
        System.out.println("Select time: " + time3 + " sec");
        obTestDB.endConDB();*/
        //-------------------------------

        //----------PostgreSQL-----------
        obTestPGDB.connectDB();

        obTestPGDB.resetDB();
        //-------------------------------

    }
}
