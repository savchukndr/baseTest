package com.company;

import java.sql.*;

/**
 * Created by savch on 10.12.2016.
 * Yoj
 */
class pgsqlDB {
    private String db = null;
    private String USER = null;
    private String PASS = null;
    private String JDBC_DRIVER = null;
    private Connection conn = null;
    private Statement stmt = null;

    pgsqlDB(String db, String USER, String PASS){
        this.db = db;
        this.USER = USER;
        this.PASS = PASS;
        JDBC_DRIVER = "org.postgresql.Driver";
    }

    public void connectDB(){
        try{
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to a selected database(PostgreSQL)...");
            String DB_URL = "jdbc:postgresql://localhost:5432/" + db;
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Connected database successfully...\n");
    }

    public void insertIntoTableDB(String model, String engine, int count){

    }

    public void insertIntoTableDB(String name, int id_car, int count){

    }

    public void resetDB(){
        try{
            System.out.println("Reseting table... (PostgreSQL)");
            stmt = conn.createStatement();

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

    }

    public void endConDB(){

    }

   /* public void dropTableDB(){

    }*/

   /* public void createTableDB(){
        try{

            System.out.println("Create table \"car\"... (PostgreSQL)");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE car\n" +
                    "(\n" +
                    "  id SERIAL NOT NULL,\n" +
                    "  model VARCHAR(225),\n" +
                    "  engine VARCHAR(225),\n" +
                    "  PRIMARY KEY (id)\n" +
                    ")";
            stmt.executeUpdate(sql);
            System.out.println("Table \"car\" created! (PostgreSQL)\n");

            System.out.println("Creating table \"master\"... (PostgreSQL)");
            sql = "CREATE TABLE master\n" +
                    "(\n" +
                    "  id     SERIAL NOT NULL,\n" +
                    "  name   VARCHAR(225),\n" +
                    "  PRIMARY KEY (id),\n" +
                    "  id_car INTEGER REFERENCES car\n" +
                    ")";
            stmt.executeUpdate(sql);
            System.out.println("Table \"master\" created! (PostgreSQL)\n");

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Connected database successfully...");
    }*/
}
