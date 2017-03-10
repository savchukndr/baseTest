package com.company.databases;


import com.company.database_handler.Connector;
import interfaces.DataBaseInterface;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.util.*;

/**
 * Created by savch on 19.12.2016.
 * All rights is okey =)
 */
public class redisDB implements DataBaseInterface {
    private Jedis jedis = null;
    private Connector conect = null;
    private int numRow = 0;

    public redisDB(int amountOfRaws){
        numRow = amountOfRaws;
        conect =  new Connector();
    }

    public Jedis connectDB(){
        try{
            conect.connectRD();
            jedis = conect.getJedis();
        }catch (Exception e){
            System.out.println("Error in Redis is: " + e);
        }
        return jedis;
    }

    public void deleteRecord(Jedis jd){
        try {
            jd.flushAll();
        }catch (Exception e){
            System.out.println("Error \"ideleteRecord()\" is: " + e);
        }
    }
}
