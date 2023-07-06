package cn.doit.doit.day01;

import org.apache.commons.lang3.RandomUtils;
import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 *    1.你得写代码=》uuid字符串
 *    2.将jar包放在生产队列里面 =》 先进先出
 *    3.生产队列里面的人物放到缓冲区
 *    4.缓冲区的代码进行消费
 *    5.如果运行成功就结束了
 *    6.如果失败放回到缓冲区重跑
 *    7.失败次数超过3次，放会到生产者
 */
public class _03_生产者 {
    public static void main(String[] args) throws InterruptedException {
         Jedis jedis = JedisUtil.getJedis();

         //启动线程
        new Thread(new ProducerThread(jedis)).start();

        Thread.sleep(20000);

        //多线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String cache_task = jedis.brpoplpush("producer_task", "cache_task",100);
                    System.out.println("任务放回到缓冲区:"+cache_task);

                    //搞个随机数来确认哪些任务失败，哪些任务成功
                    int random = RandomUtils.nextInt(1000, 10000);
                    if (random%3==0){
                        //消费缓冲区中的task
                        String task = jedis.brpoplpush("cache_task","cache_task",100);
                        System.out.println("任务失败，重新被放回到缓冲区:"+task);
                    }else{
                        String task = jedis.rpop("cache_task");
                        System.out.println("缓冲区中的任务被消费成功:"+task);
                    }
                    try {
                        Thread.sleep(RandomUtils.nextInt(100, 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}

class ProducerThread implements Runnable {
    private Jedis jedis;
    public ProducerThread(Jedis jedis){
        this.jedis = jedis;
    }

    @Override
    public void run() {
        System.out.println("41期年薪百万的程序们开始生产有bug的代码了........");
        while (true){
            try {
                UUID uuid = UUID.randomUUID();
                System.out.println("生产的task名称为："+uuid);
                jedis.lpush("producer_task",uuid.toString());
                Thread.sleep(RandomUtils.nextInt(100,500));
                if (jedis.llen("producer_task") >= 10 ){
                    Thread.sleep(5000);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
