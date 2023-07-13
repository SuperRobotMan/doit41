package day02;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.load(new BufferedInputStream(new FileInputStream("D:\\develop\\ideaWorkSpace\\doit41\\kafkaDemo41\\src\\main\\resources\\kafka.properties")));
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
    }
}
