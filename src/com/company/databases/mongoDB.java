package com.company.databases;

import interfaces.DataBaseInterface;
import com.mongodb.*;

import java.util.*;

/**
 * Created by savch on 12.12.2016.
 * All rights is okey =)
 */
public class mongoDB implements DataBaseInterface {
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

            DBCollection coll = db.getCollection("master");
            System.out.println("Collection car selected successfully");
            BasicDBObject query = new BasicDBObject().append("name", 1).append("car.model", 1).append("car.engine", 1);
            BasicDBObject field = new BasicDBObject();

            DBCursor cursor = coll.find(field, query);
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

    private static void fillCar(){
        String Model , Engine;
        int id_car = 1;
        lstDBOdbj = new ArrayList<>();
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
        return lstDBOdbj.get(index);
    }

    void insertDoc(int n){
        String name;
        DBCollection coll = db.getCollection("master");
        System.out.println("Collection \"master\" selected successfully");

        for(int i=1; i <= n; i++) {
            name = "Name" + String.valueOf(i);
            //
            BasicDBObject docMaster = new BasicDBObject("_id", i).
                    append("name", name).
                    append("car", anyBasicDBObj());

            coll.insert(docMaster);
            System.out.println("Document [" + i + "] inserted successfully into \"master\"\n");
        }
    }

    void dropColl(){
        try{
            DBCollection coll = db.getCollection("master");
            coll.drop();

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Collection \"master\" droped successfully!");
    }
}
