package cn.monitor4all.logRecord.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yangzhendong
 */
@Data
@ConfigurationProperties(prefix = "log-record")
public class LogRecordProperties {

    private RabbitMqProperties rabbitMqProperties;

    private RocketMqProperties rocketMqProperties;

    private KafkaMqProperties kafkaMqProperties;

    /**
     * choose pipeline for message: rabbitMq, rocketMq
     */
    private String dataPipeline;

    @Data
    public static class RabbitMqProperties {
        private String host;
        private int port;
        private String username;
        private String password;
        private String queueName;
        private String exchangeName;
        private String routingKey;
    }

    @Data
    public static class RocketMqProperties {
        private String topic = "logRecord";
        private String tag = "";
        private String namesrvAddr = "localhost:9876";
        private String groupName = "logRecord";
        private int maxMessageSize = 4000000;
        private int sendMsgTimeout = 3000;
        private int retryTimesWhenSendFailed = 2;
    }

    @Data
    public static class KafkaMqProperties {
        private String topic = "logRecord";
        private String bootstrapServers="localhost:9092";
        private int retries = 0;
        private int batchSize = 4096;
        private int bufferMemory = 409600;
        private String protocol = "SASL_PLAINTEXT";
        private String mechanism = "PLAIN";
    }
}
