package day02;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.record.TimestampType;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class _01_指定位置开始消费 {
    public static void main(String[] args) {
        //创建HashMap
        Map<String, Object> map = new HashMap<>();
        /**
        必须配置的参数
         */
        //key的反序列化
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //value的反序列化
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //kafka集群的地址
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"linux01:9092");
        //组id
        map.put(ConsumerConfig.GROUP_ID_CONFIG,"java01");
        /**
         * 可选择配置的
         */
        //从哪里读取数据  reset  最后才会看他
        //1.他会查看代码中有没有指定从什么地方开始消费
        //2.看这个组id是否在__consumer_offsets这个topic中有记录
        //如果说有记录，就紧接着上次消费的地方开始消费
        //如果没有记录 他才会看下面这个reset的参数，
//        map.put("auto.offset.reset","latest");
        //允许自动提交偏移量
        map.put("enable.auto.commit","true");
        //自动提交偏移量的间隔时间，当然，如果enable.auto.commit = false 那么这个参数就没有意义了
//        map.put("auto.commit.interval.ms","10000");

        //创建一个kafka的消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(map);

        //到哪个topic里面拉取数据==》订阅主题
        consumer.subscribe(Arrays.asList("consumertest"));//默认消费所有分区的数据

        TopicPartition tp = new TopicPartition("consumertest", 2);
        TopicPartition tp1 = new TopicPartition("consumertest", 1);
        TopicPartition tp2 = new TopicPartition("doit41", 1);
        //指定分区，直接分配给该消费者哪几个分区
        consumer.assign(Arrays.asList(tp,tp1,tp2));
        /**
         * TopicPartition:
         * long:offset
         */
        consumer.seek(tp,1);
        consumer.seek(tp1,2);
        consumer.seek(tp2,0);

        while (true){
            //poll拉取数据
            ConsumerRecords<String, String> records= consumer.poll(Duration.ofMillis(Integer.MAX_VALUE));

            //对数据进行处理   业务数据的处理
            for (ConsumerRecord<String, String> record : records) {
                String topic = record.topic();
                int partition = record.partition();
                long offset = record.offset();
                long timestamp = record.timestamp();
                //两种  第一种 数据写入到kafka的时间 appendTime
                //叫数据创建的时间    默认的
                TimestampType timestampType = record.timestampType();
                //我们读取出来的数据
                String value = record.value();
                System.out.println("topic:"+topic+",partition:"+partition+",offset:"+offset+",:value:"+value+",timestampType:"+timestampType+",timestamp:"+timestamp);
            }

        }

    }
}
