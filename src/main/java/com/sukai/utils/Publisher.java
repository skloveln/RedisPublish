package com.sukai.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Publisher extends Thread{

    private final JedisPool jedisPool;

    public Publisher(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public void run() {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Jedis jedis = jedisPool.getResource();   //连接池中取出一个连接
        boolean flag = true;
        int count = 0;
        while (flag) {
//            String line = null;
            try {
//                line = reader.readLine();
//                if (!"quit".equals(line)) {
                    jedis.publish("channel", ""+ ++count);

//                    Thread.sleep(1000);

                    if(count >= 100){
                        flag = false;
                    }
//                } else {
//                    break;
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        jedis.close();
    }
}