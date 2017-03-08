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
    //private int k = 1;
    private Connector conect = new Connector();
    private Connection connPG = null;
    private String resInsertIntoTable = "";

    public Statement getStmt() {
        return stmt;
    }

    public Connector getConect() {
        return conect;
    }

    public Connection getConnPG() {
        return connPG;
    }

    public String getResInsertIntoTable() {
        return resInsertIntoTable;
    }

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

    public String insertIntoTableDB(String model, String engine, int count){
        try{
            //System.out.println("Inserting [" + count + "] records into the car table");
            stmt = connPG.createStatement();

            String sql = "INSERT INTO car(model, engine) VALUES('" + model + "', '" + engine + "');";
            stmt.executeUpdate(sql);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        //System.out.println("Inserted record [" + count + "] into the car table...\n");
        return "Inserted record [" + count + "] into the car table...\n";
    }

    public String insertIntoTableDB(String name, int id_car, int count){
        try{
            //System.out.println("Inserting [" + count + "] records into the master table");
            stmt = connPG.createStatement();

            String sql = "INSERT INTO master(name, id_car) VALUES('" + name + "', " + id_car + ")";
            stmt.executeUpdate(sql);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        //System.out.println("Inserted record [" + count + "] into the master table...\n");
        return "Inserted record [" + count + "] into the master table...\n";
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

    /*public String selectRecordTableDB(){
        String selectRes = "";
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

                selectRes = "Name: " + name + ", Model: " + model + ", Engine: " + engine;

                //Display values
                *//*System.out.print("Name: " + name);
                System.out.print(", Model: " + model);
                System.out.print(", Engine: " + engine);
                System.out.println();*//*
            }
            rs.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        return selectRes;
    }*/

}
