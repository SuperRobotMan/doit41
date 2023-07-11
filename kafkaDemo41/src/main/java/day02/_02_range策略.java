package day02;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.record.TimestampType;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class _02_range策略 {
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
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "linux01:9092");
        //组id
        map.put(ConsumerConfig.GROUP_ID_CONFIG, "java01");
        map.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.CooperativeStickyAssignor");

        //创建一个kafka的消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(map);

        //到哪个topic里面拉取数据==》订阅主题
        consumer.subscribe(Arrays.asList("reb-1","reb-2"),new ConsumerRebalanceListener() {
            /**
             * 代表的是回收前具体的策略
             * @param partitions
             */
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                System.out.println("回收前的分区情况"+partitions);
            }

            /**
             * 再分配以后 分配完成之后的分区情况
             * @param partitions
             */
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                System.out.println("在分配之后的结果："+partitions);
            }
        });


        while (true) {
            //poll拉取数据
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(Integer.MAX_VALUE));
        }


    }
}
