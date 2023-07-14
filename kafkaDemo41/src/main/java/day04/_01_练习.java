package day04;

import day03.MyPartitioner;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * 从一个topic中消费数据
 * 写入到另外一个topic中
 * 开启事务
 * 开启幂等性
 */
public class _01_练习 {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"linux01:9092,linux02:9092,linux03:9092");
        //设置幂等性是true
        props.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,"true");
        //设置幂等性后必须得加的三个参数
        props.setProperty(ProducerConfig.ACKS_CONFIG,"-1");
        props.setProperty(ProducerConfig.RETRIES_CONFIG,"3");
        props.setProperty(ProducerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG,"300");
        props.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,"3");
        //"transactional.id"   给他一个事务id
        props.setProperty(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"abc");


        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"linux01:9092,linux02:9092,linux03:9092");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG,"test");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        //创建一个生产者对象  kafka里面，存的收拾key value的数据
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        //初始化事务
        producer.initTransactions();

        consumer.subscribe(Arrays.asList("doit41"));

        while (true){
            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(Integer.MAX_VALUE));
            try {
                //开启事务
                producer.beginTransaction();
                for (ConsumerRecord<String, String> record : poll) {
                    String json = record.value();
                    ProducerRecord<String, String> test01 = new ProducerRecord<>("test01", json);
                    producer.send(test01);
                    producer.flush();
                }
                //提交偏移量
                consumer.commitAsync();
                //提交事务
                producer.commitTransaction();

            } catch (ProducerFencedException e) {
                e.printStackTrace();
                //放弃事务
                producer.abortTransaction();
            }
        }



    }
}
