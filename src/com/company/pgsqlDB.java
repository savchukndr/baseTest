package com.company;

import java.sql.*;

/**
 * Created by savch on 10.12.2016.
 * Yoj
 */
class pgsqlDB implements DataBaseInterface {
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
        System.out.println("Connected database successfully...");
    }

    public void insertIntoTableDB(String model, String engine, int count){

    }

    public void insertIntoTableDB(String name, int id_car, int count){

    }

    public void resetDB(){

    }

    public void selectRecordTableDB(){

    };

    public void endConDB(){

    };

    public void dropTableDB(){

    };

    public void createTableDB(){

    };

}
