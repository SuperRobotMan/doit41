package cn.doit.doit.day01;

import org.apache.commons.lang3.RandomUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.*;

public class _04_英雄出场 {
    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = JedisUtil.getJedis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> list = new ArrayList<>();
                list.add("剑圣");
                list.add("提莫");
                list.add("瞎子");
                list.add("儿童劫");
                list.add("托儿所");
                list.add("卡莎");
                list.add("狗头");
                list.add("酒桶");
                list.add("大保健");
                list.add("大树");
                list.add("卡牌");
                Random random = new Random();
                while (true){
                    int index = random.nextInt(list.size());
                    String hero = list.get(index);
//                    System.out.println("骑着小毛驴儿出场了:"+hero);
                    //添加到redis里面
                    jedis.zincrby("hero",1,hero);
                    try {
                        Thread.sleep(RandomUtils.nextInt(100,200));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //每隔5s返回结果。结果取前top3 jdk里面有一个任务调度
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //我的任务是什么？
                Set<Tuple> hero = jedis.zrevrangeWithScores("hero", 0, 2);
                for (Tuple tuple : hero) {
                    String element = tuple.getElement();
                    double score = tuple.getScore();
                    System.out.println("当前时间是："+System.currentTimeMillis()+":"+element +"  =>  "+score);
                }
                System.out.println("==================================");

            }
        };

        //任务调度的方法
        timer.schedule(timerTask,10000,5000);


    }
}
