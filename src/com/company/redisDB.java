package com.company;


import redis.clients.jedis.Jedis;
/**
 * Created by savch on 19.12.2016.
 * All rights is okey =)
 */
class redisDB {
    void connectDB(){
        try{
            Jedis jedis = new Jedis("localhost");
            System.out.println("Connection to server sucessfully");
            //check whether server is running or not
            System.out.println("Server is running: " + jedis.ping());
        }catch (Exception e){
            System.out.println("Error in Redis is: " + e);
        }
    }
}
