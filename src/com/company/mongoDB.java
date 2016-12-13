package com.company;

import com.mongodb.*;

import java.sql.DriverManager;
import java.util.*;

/**
 * Created by savch on 12.12.2016.
 * All rights is okey =)
 */
class mongoDB implements DataBaseInterface{
    private DB db;
    private static List<BasicDBObject> lstDBOdbj;
    private Random randomGenerator = new Random();

    mongoDB(){
    }

    void connectDB(){
        try{

            // To connect to mongodb server
            MongoClient mongo = new MongoClient("localhost", 27017);
            db = mongo.getDB("TestMDB");
            System.out.println("Connect to database successfully");

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }



    void retreiveCol(){
        try{

            DBCollection coll = db.getCollection("car");
            System.out.println("Collection car selected successfully");

            DBCursor cursor = coll.find();
            int i = 1;

            while (cursor.hasNext()) {
                System.out.println("Inserted Document: "+i);
                System.out.println(cursor.next());
                i++;
            }

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    void createCol(){
        try{
            /*DBCollection collCar = db.createCollection("car", new BasicDBObject());
            System.out.println("Collection created successfully");*/

            DBCollection collMaster = db.createCollection("master", new BasicDBObject());
            System.out.println("Collection created successfully");

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    static void fillCar(){
        String randomModels, randomEngines, Model , Engine;
        int id_car = 1,idx, idx1;
        lstDBOdbj = new ArrayList<>();

        /*idx = new Random().nextInt(models.length - 1) + 1;
        idx1 = new Random().nextInt(engines.length - 1) + 1;
        randomModels = (models[idx]);
        randomEngines = (engines[idx1]);*/

        /*id_car = rn.nextInt(28 - 1) + 1;
        idSet.remove(28);*/
        for(int i=1; i < models.length; i++) {
            for (int j=1; j < engines.length; j++) {
                Model = models[i];
                Engine = engines[j];
                lstDBOdbj.add(new BasicDBObject("_id", id_car).
                        append("model", Model).
                        append("engine", Engine));
                id_car++;
            }
        }
    }

    private BasicDBObject anyBasicDBObj()
    {
        fillCar();
        int index = randomGenerator.nextInt(lstDBOdbj.size() - 1) + 1;
        //System.out.println("Managers choice this week" + item + "our recommendation to you");
        return lstDBOdbj.get(index);
    }

    void insertDoc(int n){
        String name;
        DBCollection coll = db.getCollection("master");
        System.out.println("Collection \"master\" selected successfully");

        for(int i=1; i <= n/2; i++) {
            name = "Name" + String.valueOf(i);
            //
            BasicDBObject docMaster = new BasicDBObject("_id", i).
                    append("name", name).
                    append("car", anyBasicDBObj());

            coll.insert(docMaster);
            System.out.println("Document inserted successfully into \"master\"");
        }
    }
}
