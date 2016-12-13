package com.company;
import com.sun.xml.internal.ws.api.pipe.Engine;

import java.sql.*;
import java.util.Random;

/**
 * Created by savch on 08.12.2016.
 * Yoj
 */
class mysqlDB implements DataBaseInterface{
    String db = null;
    String USER = null;
    String PASS = null;
    private String JDBC_DRIVER = null;
    private Connection conn = null;
    private Statement stmt = null;

    mysqlDB(String db, String USER, String PASS){
        this.db = db;
        this.USER = USER;
        this.PASS = PASS;
        JDBC_DRIVER = "com.mysql.jdbc.Driver";
    }

    void connectDB() {
        try{
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to a selected database... (MySQL)");
            String DB_URL = "jdbc:mysql://localhost:3306/" + db;
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully... (MySQL)");
        }catch(SQLException se){
            //Handle errors for JDBC
            System.out.println("Data Base connection error: " + se);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    void insertIntoTableDB(String name, int id_car, int count){
        String sql;

        try{
            System.out.println("Inserting [" + count + "] record into the master table");

            stmt = conn.createStatement();
            sql = "SET FOREIGN_KEY_CHECKS = 0";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO master(name, id_car) " +
                    "VALUES ('" + name + "', " + id_car + ")";
            stmt.executeUpdate(sql);
            sql = "SET FOREIGN_KEY_CHECKS=1";
            stmt.executeUpdate(sql);

            System.out.println("Inserted record [" + count + "] into the master table...\n");

        }catch(SQLException se){
            //Handle errors for JDBC
            System.out.println("Data Base connection error: " + se);
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }//end try
    }

    void resetDB(){
        try{
            System.out.println("Reseting table... (MySQL)");
            stmt = conn.createStatement();

            String sql = "TRUNCATE car,master";
            stmt.executeUpdate(sql);
            System.out.println("Table reseted! (MySQL)\n");

        }catch(SQLException se){
            //Handle errors for JDBC
            System.out.println("Data Base connection error: " + se);
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    void selectRecordTableDB(){
        try{
            System.out.println("Selecting row..");
            stmt = conn.createStatement();

            String sql = "SELECT name, model, engine FROM car, master WHERE master.id_car = car.id" ;
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

        }catch(SQLException se){
            //Handle errors for JDBC
            System.out.println("Data Base connection error: " + se);
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    void endConDB(){
        System.out.println("Closing connection... (MySQL)");
        try{
            if(stmt!=null)
                conn.close();
        }catch(SQLException se){
        }// do nothing
        try{
            if(conn!=null)
                conn.close();
        }catch(SQLException se){
            System.out.println("Data Base connection error: " + se);
        }
        System.out.println("Connection closed! (MySQL)");
    }

    void dropTableDB(String table1, String table2){
        try{
            System.out.println("Droping tables... (MySQL)");
            stmt = conn.createStatement();

            String sql = "DROP TABLE " + table1;
            stmt.executeUpdate(sql);
            sql = "DROP TABLE " + table2;
            stmt.executeUpdate(sql);
            System.out.println("Tables Droped! (MySQL)\n");

        }catch(SQLException se){
            //Handle errors for JDBC
            System.out.println("Data Base connection error: " + se);
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    void createTableDB(){
        try{
            System.out.println("Creating table \"car\" ... (MySQL)");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE car\n" +
                    "(\n" +
                    "  id int NOT NULL AUTO_INCREMENT,\n" +
                    "  model VARCHAR(225),\n" +
                    "  engine VARCHAR(225),\n" +
                    "  PRIMARY KEY (id)\n" +
                    ")";
            stmt.executeUpdate(sql);
            System.out.println("Table \"car\" created! (MySQL)\n");

            System.out.println("Creating table \"master\"... (MySQL)");
            sql = "CREATE TABLE master\n" +
                    "(\n" +
                    "  id     SERIAL NOT NULL,\n" +
                    "  name   VARCHAR(225),\n" +
                    "  PRIMARY KEY (id),\n" +
                    "  id_car INTEGER REFERENCES car)";
            stmt.executeUpdate(sql);
            System.out.println("Table \"master\" created! (MySQL)\n");

        }catch(SQLException se){
            //Handle errors for JDBC
            System.out.println("Data Base connection error: " + se);
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    void insertData(mysqlDB ob, String tableName, int n){
        int idx, idx1, id_car, k = 1;//, countCar = 1;
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
                for(int i=1; i <= n/2; i++) {
                    name = "Name" + String.valueOf(i);
                    id_car = rn.nextInt(28 - 1) + 1;
                    ob.insertIntoTableDB(name, id_car, i);
                }
                break;
            default:
                throw new IllegalArgumentException(tableName);
        }
    }
}
