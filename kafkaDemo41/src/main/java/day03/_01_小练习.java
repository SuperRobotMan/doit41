package day03;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 造10条数据 发送给kafka的topic
 * 要求：10条数据都发送给该topic的1号分区=》重写分区器
 * 开启幂等性发送
 *
 *
 * 幂等性 数据往里面写不会重复
 * 事务==》 让所有的动作都成功
 *
 * 精准一次性  ==》 漏写   重复写   乱序的问题   broker   有leader  有副本  还有容错机制  ==》消费者  精准一次性消费
 */

/**
 * 从一个topic中讲数据读出来，然后在写回到另外一个topic中
 * 写数据的时候，开启幂等性  开启事务啊
 *
 */
public class _01_小练习 {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"linux01:9092,linux02:9092,linux03:9092");

        //重写一个分区器
        props.setProperty("partitioner.class", MyPartitioner.class.getName());

        //设置幂等性是true
        props.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,"true");
        //设置幂等性后必须得加的三个参数
        props.setProperty(ProducerConfig.ACKS_CONFIG,"-1");
        props.setProperty(ProducerConfig.RETRIES_CONFIG,"3");
        props.setProperty(ProducerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG,"300");
        props.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,"3");

        //"transactional.id"   给他一个事务id
        props.setProperty(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"abc");

        //创建一个生产者对象  kafka里面，存的收拾key value的数据
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        //初始化事务
        producer.initTransactions();
        //开启事务
        try {
            producer.beginTransaction();
            //业务处理的具体的逻辑

            ProducerRecord<String, String> record = new ProducerRecord<>("doit41", "abc");
            producer.send(record);

            //提交事务
            producer.commitTransaction();
        } catch (ProducerFencedException e) {
            e.printStackTrace();
            //放弃事务
            producer.abortTransaction();
        }
    }
}
