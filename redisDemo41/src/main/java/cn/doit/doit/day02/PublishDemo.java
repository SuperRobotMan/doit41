package cn.doit.doit.day02;

import cn.doit.doit.day01.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Locale;

public class PublishDemo {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedis();
//        jedis.publish("A","this message from idea");

        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                //写具体的业务逻辑
                if (channel.equals("A")){
                    System.out.println(message.toUpperCase(Locale.ROOT)+"==> A");
                }else if(channel.equals("B")){
                    System.out.println(message.toLowerCase(Locale.ROOT)+"==> B");
                }else{
                    System.out.println(message+"==> C");
                }
            }
        },"A","B","C");

    }
}
