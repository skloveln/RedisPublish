package com.sukai.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisUtils {


    @Test
    public void publish(){
        new RedisService(new JedisPool());
        Cache cache = Redis.use();
        Jedis jedis = cache.getJedis();
        jedis.publish("channel", "This is a message");

        jedis.close();

    }

    public static void push(){
        Cache cache = Redis.use();
        if(cache == null){
            new RedisService(new JedisPool());
            cache = Redis.use();
        }

        for(int i=0; i<100; ) {
            cache.lpush("test", ++i);
            System.out.println("lpush:  " + i);
        }

    }

    public static void pop(){
        Cache cache = Redis.use();
        if(cache == null){
            new RedisService(new JedisPool());
            cache = Redis.use();
        }

        for(int j=0; true; j++) {
            String str = cache.rpop("test");
            if(StringUtils.isBlank(str)){
                break;
            }
            System.out.println("rpop:" + str);
        }
    }

    @Test
    public void subscriber(){

        SubThread subThread = new SubThread(new JedisPool());
        subThread.start();
    }

    @Test
    public void publisher(){
        Publisher publisher = new Publisher(new JedisPool());
        publisher.start();
    }

    public static void sendAndReceive() throws Exception{
        new RedisService(new JedisPool());
        Cache cache = Redis.use();
        JedisPool jedisPool = cache.jedisPool;

        pop();
        push();

        Publisher publisher = new Publisher(jedisPool);
        SubThread subscriber = new SubThread(jedisPool);

        subscriber.start();

        Thread.sleep(2000);

        publisher.start();
    }

    public static void main( String[] args ) throws Exception{

        sendAndReceive();

    }
}
