package cn.doit.doit.day01;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisCliDemo {
    public static void main(String[] args) {
        //获取连接对象
//        Jedis jedis = new Jedis("linux01",6379);

        //连接池
//        JedisPool poll = new JedisPool("linux01",6379);
//        Jedis jedis = poll.getResource();

        Jedis jedis = JedisUtil.getJedis();

        //调用api进行操作
        String value = jedis.get("name");
        System.out.println(value);


        //关闭资源
        jedis.close();





    }
}
