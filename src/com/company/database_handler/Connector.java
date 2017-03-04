package com.company.database_handler;

import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by savch on 04.03.2017.
 */
public class Connector {
    private Jedis jedis;
    private String db = "postgres";
    private String USER = "postgres";
    private String PASS = "savchukao19";
    private Connection conn = null;
    private String JDBC_DRIVER = "org.postgresql.Driver";

    public void connectPG(){
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

    public void connectRD(){
        try{
            jedis = new Jedis("localhost");
            System.out.println("Connection to server sucessfully");
            //check whether server is running or not
            System.out.println("Server is running: " + jedis.ping());
        }catch (Exception e){
            System.out.println("Error in Redis is: " + e);
        }
    }

    public void disconnect(){
        try{
            if(conn!=null)
                conn.close();
        }catch(SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        System.out.println("Successfully closed connection to database ...");
    }

    public Jedis getJedis() {
        return jedis;
    }

    public Connection getConn() {
        return conn;
    }
}
