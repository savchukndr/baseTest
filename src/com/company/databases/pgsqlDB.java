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
    private Connector conect;
    private Connection connPG = null;

    public pgsqlDB(){
        conect = new Connector();
    }

    public Connection getConnPG() {
        return connPG;
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
            stmt = connPG.createStatement();
            String sql = "INSERT INTO car(model, engine) VALUES('" + model + "', '" + engine + "');";
            stmt.executeUpdate(sql);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return "Inserted record [" + count + "] into the car table...\n";
    }

    public String insertIntoTableDB(String name, int id_car, int count){
        try{
            stmt = connPG.createStatement();
            String sql = "INSERT INTO master(name, id_car) VALUES('" + name + "', " + id_car + ")";
            stmt.executeUpdate(sql);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
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
}
