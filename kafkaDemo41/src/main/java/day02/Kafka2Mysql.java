package day02;

import com.alibaba.fastjson.JSON;
import kunkun.UserInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.lang.reflect.Array;
import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

public class Kafka2Mysql {
    public static void main(String[] args) throws InterruptedException, SQLException {
        Properties props = new Properties();
        /**
         * 生产者的相关属性
         */
      /*  props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"linux01:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        props.setProperty(ProducerConfig.ACKS_CONFIG,"-1");*/
        /**
         * 消费者的相关属性
         */
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"linux01:9092");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG,"g02");
        //出现两个问题，重复消费或者漏处理数据
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
//        props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"10000");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        props.setProperty(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG,"true");

        //数据库的连接
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");
        PreparedStatement pps = conn.prepareStatement("insert into user_info values (?,?,?,?)");
        PreparedStatement tpPPS = conn.prepareStatement("insert into tp values(?,?) on DUPLICATE key update offset = ?");
        PreparedStatement getOffset = conn.prepareStatement("select offset from tp where g_t_p = ?");



        //jdbc 默认就是提交事务的
        conn.setAutoCommit(false);

/*
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        ArrayList<String> list = new ArrayList<>();
        list.add("male");
        list.add("female");

        UserInfo userInfo = new UserInfo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    userInfo.setId(RandomUtils.nextInt(100,100000));
                    userInfo.setAge(RandomUtils.nextInt(10,100));
                    userInfo.setGender(list.get(RandomUtils.nextInt(0,list.size())));
                    userInfo.setName(RandomStringUtils.randomAlphabetic(10));
                    String json = JSON.toJSONString(userInfo);
                    ProducerRecord<String, String> userinfo = new ProducerRecord<>("userinfo", json);
                    producer.send(userinfo);
                    producer.flush();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("kafka2mysql"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {

            }
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                for (TopicPartition partition : partitions) {
                    String topic = partition.topic();
                    int partition1 = partition.partition();
                    try {
                        getOffset.setString(1,"g02_"+topic+"_"+partition1);
                        ResultSet resultSet = getOffset.executeQuery();
                        while (resultSet.next()){
                            long offset = resultSet.getLong("offset");
                            consumer.seek(partition,offset+1);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });




        while (true){
            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(Integer.MAX_VALUE));
            for (ConsumerRecord<String, String> record : poll) {
                try {
                    //业务数据的处理
                    String json = record.value();
//                    UserInfo userInfo1 = JSON.parseObject(json, UserInfo.class);
                    String[] arr = json.split(",");
                    //将数据都写入到数据库中
                    //给？设置值
                    pps.setInt(1,Integer.parseInt(arr[0]));
                    pps.setString(2,arr[1]);
                    pps.setInt(3,Integer.parseInt(arr[2]));
                    pps.setString(4,arr[3]);
                    pps.execute();

                    if (arr[0].equals("6")){
                        throw new Exception("老子自己造的异常,不服来打我");
                    }

                    //偏移量的提交
                    String topic = record.topic();
                    int partition = record.partition();
                    long offset = record.offset();
                    tpPPS.setString(1,"g02_"+topic+"_"+partition);
                    tpPPS.setLong(2,offset);
                    tpPPS.setLong(3,offset);
                    tpPPS.execute();

                    //提交事务
                    conn.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    //回滚事务
                    conn.rollback();
                }


            }
            Thread.sleep(1000);
            consumer.commitSync();//可以精准一次性消费呢？？？
        }



    }
}
