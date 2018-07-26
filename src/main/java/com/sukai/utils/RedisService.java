package com.sukai.utils;


import com.sukai.utils.serializer.ISerializer;
import com.sukai.utils.serializer.JdkSerializer;
import redis.clients.jedis.JedisPool;

/**
 * Description:Redis插件
 * Author: sukai
 * Date: 2017-08-11
 */
public class RedisService extends Cache{


    public RedisService(JedisPool jedisPool){
        super("main-cache", jedisPool, JdkSerializer.me, IKeyNamingPolicy.defaultKeyNamingPolicy);
        Redis.addCache(this);
    }

}


