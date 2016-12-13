package com.company;

import com.mongodb.*;

import java.sql.DriverManager;
import java.util.Random;

/**
 * Created by savch on 12.12.2016.
 * All rights is okey =)
 */
class mongoDB implements DataBaseInterface{
    DB db;
    MongoClient mongo;

    mongoDB(){
    }

    void connectDB(){
        try{

            // To connect to mongodb server
            mongo = new MongoClient("localhost", 27017);
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

    void insertDoc(int n){
        int idx, idx1, id_car;//, countCar = 1;
        String randomModels, randomEngines, name;
        Random rn = new Random();
        /*for(int i=1; i <= n/2; i++) {
            name = "Name" + String.valueOf(i);
            id_car = rn.nextInt(n - 1) + 1;
            DBCollection coll = db.getCollection("master");
            System.out.println("Collection \"master\" selected successfully");

            BasicDBObject docMaster = new BasicDBObject("_id", i).
                    append("name", name).
                    append("id_car", id_car);

            coll.insert(docMaster);
            System.out.println("Document inserted successfully into \"masterr\"");
        }*/
        DBCollection coll = db.getCollection("master");
        System.out.println("Collection \"master\" selected successfully");

        for(int i=1; i <= n/2; i++) {
            idx = new Random().nextInt(models.length - 1) + 1;
            idx1 = new Random().nextInt(engines.length - 1) + 1;
            randomModels = (models[idx]);
            randomEngines = (engines[idx1]);


            id_car = rn.nextInt(n - 1) + 1;
            BasicDBObject docCar = new BasicDBObject("_id", id_car).
                    append("model", randomModels).
                    append("engine", randomEngines);

            name = "Name" + String.valueOf(i);
            //
            BasicDBObject docMaster = new BasicDBObject("_id", i).
                    append("name", name).
                    append("car", docCar);

            coll.insert(docMaster);
            System.out.println("Document inserted successfully into \"car\"");

            /*DBObject document1 = new BasicDBObject();
            document1.put("name", "Kiran");
            document1.put("age", 20);

            DBObject document2 = new BasicDBObject();
            document2.put("name", "John");

            List<DBObject> documents = new ArrayList<>();
            documents.add(document1);
            documents.add(document2);
            collection.insert(documents);*/
        }
    }
}
