package com.company;
import java.util.Random;
import java.util.Date;

public class Main {
    private static String models[] = {"KIA", "Mersedes", "Ferarri", "Honda", "Hundai", "Tesla",
            "Lada", "Tavria"};
    private static String engines[] = {"v10", "v12", "v6", "v4", "govno"};

    static void insertData(DataBase ob, String tableName, int n){
        int idx, idx1, id_car;
        String randomModels, randomEngines, name;

        switch (tableName) {
            case "car":
                for(int i=1; i <= n; i++) {
                    idx = new Random().nextInt(models.length);
                    idx1 = new Random().nextInt(engines.length);
                    randomModels = (models[idx]);
                    randomEngines = (engines[idx1]);
                    ob.insertIntoDB(randomModels, randomEngines, i);
                }
                break;
            case "master":
                Random rn = new Random();
                for(int i=1; i <= n/2; i++) {
                    name = "Name" + String.valueOf(i);
                    id_car = rn.nextInt(n);
                    ob.insertIntoDB(name, id_car, i);
                }
                break;
            default:
                throw new IllegalArgumentException(tableName);
        }
    }

    static void checkTime(Date ob1, Date ob2){
        long msElapsedTime;
        double resTime;
        msElapsedTime = ob2.getTime() - ob1.getTime();
        Long longObject = new Long(String.valueOf(msElapsedTime));
        resTime = longObject.doubleValue() * 0.001;
        System.out.println("Time executing is: " + resTime + " sec");
    }

    public static void main(String[] args) {
        int age;
	    DataBase obTestDB = new DataBase("TestDB", "savchukndr", "savchukao22");
        obTestDB.connectDB();
        //obTestDB.resetDB();

        Date startDate = new Date();
        insertData(obTestDB, "car", 100);
        Date endDate = new Date();

        checkTime(startDate, endDate);

        /*Date startDate1 = new Date();
        //for(int i=1; i <= 10; i++){
            obTestDB.selectRecordDB(66778);
        //}
        Date endDate1 = new Date();

        checkTime(startDate1, endDate1);*/

        obTestDB.endConDB();
    }
}
