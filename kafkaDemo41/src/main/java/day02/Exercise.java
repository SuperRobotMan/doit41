package day02;

import com.alibaba.fastjson.JSON;
import kunkun.EventInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.time.Duration;
import java.util.*;

public class Exercise {
    public static void main(String[] args) throws InterruptedException, SQLException {
        Properties props = new Properties();
        /**
         * 生产者的相关属性
         */
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"linux01:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        props.setProperty(ProducerConfig.ACKS_CONFIG,"-1");
        /**
         * 消费者的相关属性
         */
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"linux01:9092");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG,"g03");
        //出现两个问题，重复消费或者漏处理数据
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
//        props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"10000");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        props.setProperty(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG,"true");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        HashSet<Integer> set = new HashSet<>();

        EventInfo eventInfo = new EventInfo();

        //redis  ==》 set  bitmap  hll
        Jedis jedis = new Jedis("linux01", 6379);

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");
        PreparedStatement pps = conn.prepareStatement("insert into abc values (?)");
        PreparedStatement getUV = conn.prepareStatement("select count(distinct guid) as res from abc");



        //生产者线程
     /*   new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    eventInfo.setGuid(RandomUtils.nextInt(100,10000));
                    eventInfo.setEventId(RandomStringUtils.randomAlphabetic(10));
                    eventInfo.setTimestamp(System.currentTimeMillis());
                    //转换成json串
                    String jsonString = JSON.toJSONString(eventInfo);
                    producer.send(new ProducerRecord<String,String>("guid",jsonString));
                    producer.flush();
                    try {
                        Thread.sleep(RandomUtils.nextInt(10,200));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/

        //消费者的线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                consumer.subscribe(Arrays.asList("guid"));
                while (true){
                    ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(Integer.MAX_VALUE));
                    for (ConsumerRecord<String, String> record : poll) {
                        //这个value是kafka里面的数据
                        String json = record.value();
                        EventInfo eventInfo1 = JSON.parseObject(json, EventInfo.class);
                        int guid = eventInfo1.getGuid();

                        boolean flag = set.contains(guid);
                        if (!flag){
                            eventInfo1.setIsNew(1);
                            set.add(guid);
                        }else {
                            eventInfo1.setIsNew(0);
                        }

                        System.out.println(JSON.toJSON(eventInfo1));
                    }
                    try {
                        Thread.sleep(RandomUtils.nextInt(100,300));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //定时任务的线程
      /*  new Thread(new Runnable() {
            @Override
            public void run() {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            ResultSet resultSet = getUV.executeQuery();
                            while (resultSet.next()){
                                long res = resultSet.getLong(1);
                                System.out.println("截止到"+System.currentTimeMillis()+"为止，uv的个数是："+res);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                },5000,5000);
            }
        }).start();*/



    }
}
