package cn.monitor4all.logRecord.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangzhendong
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "log-record.data-pipeline", havingValue = "kafkaMq")
@EnableConfigurationProperties({LogRecordProperties.class})
public class KafkaMqSenderConfiguration {
    private String bootstrapServers;
    private int retries;
    private int batchSize;
    private int bufferMemory;
    private String protocol;
    private String mechanism;

    @Autowired
    private LogRecordProperties properties;

    @PostConstruct
    public void rabbitMqConfig() {
        this.bootstrapServers = properties.getKafkaMqProperties().getBootstrapServers();
        this.retries = properties.getKafkaMqProperties().getRetries();
        this.batchSize = properties.getKafkaMqProperties().getBatchSize();
        this.bufferMemory = properties.getKafkaMqProperties().getBufferMemory();
        this.protocol = properties.getKafkaMqProperties().getProtocol();
        this.mechanism = properties.getKafkaMqProperties().getMechanism();

    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() throws RuntimeException {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
    }

    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put("security.protocol", protocol);
        props.put("sasl.mechanism", mechanism);
        return props;
    }
}
