package com.sukai.utils;

import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub {

    public Subscriber() {

    }

    @Override
    public void onMessage(String channel, String message) {       //收到消息会调用
        Cache cache = Redis.use();
        System.out.println(String.format("channel=%s,  length=%s", channel, cache.llen("test")));
        System.out.println(String.format("receive redis published message, channel %s, message %s", channel, message));
        Integer pop = cache.rpop("test");
        if(pop != null){
            System.out.println(String.format("channel=%s,  pop=%s", channel, pop+""));
            cache.lpush(pop+"", pop);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("+++++++  over  +++++++++");
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {    //订阅了频道会调用
        System.out.println(String.format("subscribe redis channel success, channel %s, subscribedChannels %d",
                channel, subscribedChannels));
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {   //取消订阅 会调用
        System.out.println(String.format("unsubscribe redis channel, channel %s, subscribedChannels %d",
                channel, subscribedChannels));

    }
}