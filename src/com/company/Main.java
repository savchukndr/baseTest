package com.company;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.Random;
import java.util.Date;

public class Main {

    private static double checkTime(Date ob1, Date ob2){
        long msElapsedTime;
        double resTime;
        msElapsedTime = ob2.getTime() - ob1.getTime();
        Long longObject = new Long(String.valueOf(msElapsedTime));
        return longObject.doubleValue() * 0.001;
    }

    public static void main(String[] args) {
        double time1, time2, time3, time4, time5, time6;
        int amountOfRaws = 100;

        mysqlDB obTestDB = new mysqlDB("TestDB", "savchukndr", "savchukao22");
	    pgsqlDB obTestPGDB = new pgsqlDB("postgres", "savchukndr", "savchukao22");
	    mongoDB obTestMDB = new mongoDB();

	    //--------MySQL------------
	    /*obTestDB.connectDB();
        //obTestDB.resetDB();

        obTestDB.dropTableDB("master", "car");

        obTestDB.createTableDB();

        Date startDate = new Date();
        obTestDB.insertData(obTestDB, "car", amountOfRaws);
        Date endDate = new Date();

        time1 = checkTime(startDate, endDate);

        Date startDate1 = new Date();
        obTestDB.insertData(obTestDB, "master", amountOfRaws);
        Date endDate1 = new Date();

        time2 = checkTime(startDate1, endDate1);

        Date startDate2 = new Date();
            obTestDB.selectRecordTableDB();
        Date endDate2 = new Date();

        time3 = checkTime(startDate2, endDate2);

        obTestDB.endConDB();*/
        //-------------------------------

        //----------PostgreSQL-----------
        /*obTestPGDB.connectDB();

        obTestPGDB.resetDB();

        Date startDate3 = new Date();
        obTestPGDB.insertData(obTestPGDB, "car", amountOfRaws);
        Date endDate3 = new Date();

        time4 = checkTime(startDate3, endDate3);

        Date startDate4 = new Date();
        obTestPGDB.insertData(obTestPGDB, "master", amountOfRaws);
        Date endDate4 = new Date();

        time5 = checkTime(startDate4, endDate4);

        Date startDate5 = new Date();
        obTestPGDB.selectRecordTableDB();
        Date endDate5 = new Date();

        time6 = checkTime(startDate5, endDate5);

        System.out.println("----------MySQL-----------------");
        System.out.println("Insert into car time: " + time1 + " sec");
        System.out.println("Insert into master time: " + time2 + " sec");
        System.out.println("Select time: " + time3 + " sec");

        System.out.println("----------PostgreSQL-----------------");
        System.out.println("Insert into car time: " + time4 + " sec (");
        System.out.println("Insert into master time: " + time5 + " sec");
        System.out.println("Select time: " + time6 + " sec");*/
        //-------------------------------


        //-----------Mongo----------------
        obTestMDB.connectDB();
        //obTestMDB.retreiveCol();
        obTestMDB.dropColl();
        obTestMDB.createCol();
        obTestMDB.insertDoc(amountOfRaws);
        //--------------------------------
    }
}
