package cn.monitor4all.logRecord.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan("cn.monitor4all.logRecord")
@Import({RabbitMqSenderConfiguration.class, RocketMqSenderConfiguration.class, KafkaMqSenderConfiguration.class})
public class LogRecordAutoConfiguration {

}
