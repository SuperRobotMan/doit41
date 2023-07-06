package cn.doit.doit.day01;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisUtil {
    public static Jedis getJedis(){
        JedisPool pool = new JedisPool("linux01", 6379);
        return pool.getResource();
    }
}
