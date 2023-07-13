package day01;

import day03.MyPartitioner;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {
    public static void main(String[] args) throws InterruptedException {
        //设置一些配置信息
        Properties props = new Properties();
//        org.apache.kafka.common.serialization.Serializer
        /**
         * 必须得配置的
         */
        //key的序列化方式                     org.apache.kafka.common.serialization.StringSerializer
//        props.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //value的序列化方式
//        props.setProperty("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        //设置kafka集群的地址
//        props.setProperty("bootstrap.servers","linux01:9092,linux02:9092,linux03:9092");
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"linux01:9092,linux02:9092,linux03:9092");

        props.setProperty("partitioner.class", MyPartitioner.class.getName());
        /**
         * 可选的配置
         */
        //kafka的应答机制
//        props.setProperty("acks","-1");
        props.setProperty(ProducerConfig.ACKS_CONFIG,"-1");
        //创建一个生产者对象  kafka里面，存的收拾key value的数据
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 100; i++) {
            //封装需要发送的数据
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("aaa","cbc"+i);
            //拿着生产者对象发送数据
            producer.send(record);
            //刷新一下
            producer.flush();
            Thread.sleep(10);
        }

        //关闭资源
        producer.close();
    }
}
