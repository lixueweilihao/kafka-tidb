package org.njzq.data.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author LH
 * @description:
 * @date 2021-05-25 11:53
 */
public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    public static void buildSource(Properties pro, TidbSink tidbsink) throws IllegalAccessException {
        String topic = null;
        Properties props = buildKafkaProps(pro);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(props.get("topic").toString()));
            OUT:
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                if (!records.isEmpty() || records.count() > 0) {
                    for (ConsumerRecord<String, String> record : records) {
                        logger.error("kafka接收到的数据, {}", record.value());
                        try {
                            tidbsink.write(record.value());
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error(e.toString());
                            break OUT;
                        }
                        consumer.commitSync();
                    }
                }
            }
    }
    public static Properties buildKafkaProps(Properties props) {
        Properties properties = new Properties();
        properties.put(PropertiesConstants.BOOTSTRAP_SERVERS, props.getProperty(PropertiesConstants.KAFKA_BROKERS, PropertiesConstants.DEFAULT_KAFKA_BROKERS));
        properties.put(PropertiesConstants.ZOOKEEPER_CONNECT, props.getProperty(PropertiesConstants.KAFKA_ZOOKEEEPER_CONNECT, PropertiesConstants.DEFAULT_KAFKA_ZOOKEEPER_CONNECT));
        properties.put(PropertiesConstants.GROUP_ID, props.getProperty(PropertiesConstants.KAFKA_GROUP_ID,"bigdata1") + "_" + props.getProperty(PropertiesConstants.KAFKA_TOPIC));
        properties.put(PropertiesConstants.TOPIC, props.getProperty(PropertiesConstants.KAFKA_TOPIC, PropertiesConstants.DEFAULT_KAFKA_BROKERS));
        properties.put(PropertiesConstants.KEY_DESERIALIZER, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(PropertiesConstants.VALUE_DESERIALIZER, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(PropertiesConstants.AUTO_OFFSET_RESET, "latest");
        properties.setProperty("enable.auto.commit", "false");
//        properties.setProperty("auto.commit.interval.ms", "1000");
        properties.put("session.timeout.ms","30000");
        return properties;
    }
}
