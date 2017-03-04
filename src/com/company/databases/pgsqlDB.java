package com.company.databases;

import com.company.database_handler.Connector;
import interfaces.DataBaseInterface;

import java.sql.*;
import java.util.Random;

/**
 * Created by savch on 10.12.2016.
 * Yoj
 */
public class pgsqlDB implements DataBaseInterface {
    private Statement stmt = null;
    private int k = 1;
    Connector conect = new Connector();
    private Connection connPG = null;

    public void connectDB(){
        try{
            conect.connectPG();
            connPG = conect.getConn();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Connected database successfully...\n");
    }

    public void insertIntoTableDB(String model, String engine, int count){
        try{
            System.out.println("Inserting [" + count + "] records into the car table");
            stmt = connPG.createStatement();

            String sql = "INSERT INTO car(model, engine) VALUES('" + model + "', '" + engine + "');";
            stmt.executeUpdate(sql);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Inserted record [" + count + "] into the car table...\n");
    }

    public void insertIntoTableDB(String name, int id_car, int count){
        try{
            System.out.println("Inserting [" + count + "] records into the master table");
            stmt = connPG.createStatement();

            String sql = "INSERT INTO master(name, id_car) VALUES('" + name + "', " + id_car + ")";
            stmt.executeUpdate(sql);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Inserted record [" + count + "] into the master table...\n");
    }

    public void resetDB(){
        try{
            System.out.println("Reseting table... (PostgreSQL)");
            stmt = connPG.createStatement();

            String sql = "TRUNCATE car, master";
            stmt.executeUpdate(sql);

            sql = "ALTER SEQUENCE car_id_seq RESTART WITH 1";
            stmt.executeUpdate(sql);

            sql = "ALTER SEQUENCE master_id_seq RESTART WITH 1";
            stmt.executeUpdate(sql);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        System.out.println("Table reseted! (PostgreSQL)\n");
    }

    public void selectRecordTableDB(){
        try{
            System.out.println("Selecting row..");
            stmt = connPG.createStatement();

            String sql = "SELECT name, model, engine FROM car, master WHERE master.id_car = car.id";
            ResultSet rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                String name  = rs.getString("name");
                String model = rs.getString("model");
                String engine = rs.getString("engine");

                //Display values
                System.out.print("Name: " + name);
                System.out.print(", Model: " + model);
                System.out.print(", Engine: " + engine);
                System.out.println();
            }
            rs.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        System.out.println("Table reseted! (PostgreSQL)\n");
    }

    public void insertData(pgsqlDB ob, String tableName, int n){
        int idx, idx1, id_car;//, countCar = 1;
        String Model , Engine, name;
        Random rn = new Random();

        switch (tableName) {
            case "car":
                for(int i=1; i < models.length; i++) {
                    for(int j=1; j < engines.length; j++) {
                        Model = models[i];
                        Engine = engines[j];
                        ob.insertIntoTableDB(Model, Engine, k);
                        //countCar++;
                        k++;
                    }
                }
                break;
            case "master":
                k--;
                for(int i=1; i <= n; i++) {
                    name = "Name" + String.valueOf(i);
                    id_car = rn.nextInt(k - 1) + 1;
                    ob.insertIntoTableDB(name, id_car, i);
                }
                break;
            default:
                throw new IllegalArgumentException(tableName);
        }
    }

}
