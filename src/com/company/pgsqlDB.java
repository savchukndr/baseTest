package com.company;

import java.sql.*;
import java.util.Random;

/**
 * Created by savch on 10.12.2016.
 * Yoj
 */
class pgsqlDB extends mysqlDB implements DataBaseInterface{
    private Statement stmt = null;
    private Connection conn = null;
    private String JDBC_DRIVER = null;
    private int k = 1;

    pgsqlDB(String db, String USER, String PASS){
       /* this.db = db;
        this.USER = USER;
        this.PASS = PASS;*/
        super(db, USER, PASS);
        JDBC_DRIVER = "org.postgresql.Driver";
    }

    void connectDB(){
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
        try{
            System.out.println("Inserting [" + count + "] records into the car table");
            stmt = conn.createStatement();

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
            stmt = conn.createStatement();

            String sql = "INSERT INTO master(name, id_car) VALUES('" + name + "', " + id_car + ")";
            stmt.executeUpdate(sql);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Inserted record [" + count + "] into the master table...\n");
    }

    void resetDB(){
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

    void selectRecordTableDB(){
        try{
            System.out.println("Selecting row..");
            stmt = conn.createStatement();

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

    public void endConDB(){

    }

    void insertData(pgsqlDB ob, String tableName, int n){
        int idx, idx1, id_car;//, countCar = 1;
        String Model , Engine, name;
        Random rn = new Random();

        switch (tableName) {
            case "car":
                for(int i=1; i < models.length; i++) {
                    for(int j=1; j < engines.length; j++) {
                    /*idx = new Random().nextInt(models.length - 1) + 1;
                    idx1 = new Random().nextInt(engines.length - 1) + 1;
                    randomModels = (models[idx]);
                    randomEngines = (engines[idx1]);*/
                        Model = models[i];
                        Engine = engines[j];
                        ob.insertIntoTableDB(Model, Engine, k);
                        //countCar++;
                        k++;
                    }
                }
                break;
            case "master":
                k = k - 1;
                for(int i=1; i <= n/2; i++) {
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
