package com.company;
import java.sql.*;

/**
 * Created by savch on 08.12.2016.
 * Yoj
 */
class mysqlDB implements DataBaseInterface{
    private String db = null;
    private String USER = null;
    private String PASS = null;
    private String JDBC_DRIVER = null;
    private Connection conn = null;
    private Statement stmt = null;

    mysqlDB(String db, String USER, String PASS){
        this.db = db;
        this.USER = USER;
        this.PASS = PASS;
        JDBC_DRIVER = "com.mysql.jdbc.Driver";
    }

    public void connectDB() {
        try{
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to a selected database...");
            String DB_URL = "jdbc:mysql://localhost:3306/" + db;
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
        }catch(SQLException se){
            //Handle errors for JDBC
            System.out.println("Data Base connection error: " + se);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertIntoTableDB(String model, String engine, int count){
        String sql = null;
        try{
            System.out.println("Inserting [" + count + "] records into the car table");
            stmt = conn.createStatement();
                sql = "INSERT INTO car(model, engine) " +
                        "VALUES ('" + model + "', '" + engine + "')";
                stmt.executeUpdate(sql);
            System.out.println("Inserted record [" + count + "] into the car table...\n");

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }//end try
    }

    public void insertIntoTableDB(String name, int id_car, int count){
        String sql = null;

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
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }//end try
    }

    public void resetDB(){
        try{
            System.out.println("Reseting table..");
            stmt = conn.createStatement();

            String sql = "TRUNCATE car";
            stmt.executeUpdate(sql);

            sql = "TRUNCATE master";
            stmt.executeUpdate(sql);
            System.out.println("Table reseted!\n");

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void selectRecordTableDB(){
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
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void endConDB(){
        System.out.println("Closing connection...");
        try{
            if(stmt!=null)
                conn.close();
        }catch(SQLException se){
        }// do nothing
        try{
            if(conn!=null)
                conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        System.out.println("Connection closed.");
    }

    public void dropTableDB(){
        try{
            System.out.println("Droping tables..");
            stmt = conn.createStatement();

            String sql = "DROP TABLE master";
            stmt.executeUpdate(sql);
            sql = "DROP TABLE car";
            stmt.executeUpdate(sql);
            System.out.println("Tables Droped!\n");

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void createTableDB(){
        try{
            System.out.println("Creating table \"car\"..");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE car\n" +
                    "(\n" +
                    "  id int NOT NULL AUTO_INCREMENT,\n" +
                    "  model VARCHAR(225),\n" +
                    "  engine VARCHAR(225),\n" +
                    "  PRIMARY KEY (id)\n" +
                    ")";
            stmt.executeUpdate(sql);
            System.out.println("Table \"car\" created!\n");

            System.out.println("Creating table \"master\"..");
            sql = "CREATE TABLE master\n" +
                    "(\n" +
                    "  id int NOT NULL AUTO_INCREMENT,\n" +
                    "  name VARCHAR(225),\n" +
                    "  id_car INT NOT NULL ,\n" +
                    "  PRIMARY KEY (id),\n" +
                    "  FOREIGN KEY (id_car) REFERENCES car(id)\n" +
                    ")";
            stmt.executeUpdate(sql);
            System.out.println("Table \"master\" created!\n");

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
}
