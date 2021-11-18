package cn.monitor4all.logRecord.service.impl;

import cn.monitor4all.logRecord.bean.LogDTO;
import cn.monitor4all.logRecord.configuration.LogRecordProperties;
import cn.monitor4all.logRecord.service.LogService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @project logRecordWebDemo
 * @description:
 * @author: lm
 * @create: 2021-11-18
 */
@Service
@Slf4j
@EnableConfigurationProperties({LogRecordProperties.class})
@ConditionalOnProperty(name = "log-record.data-pipeline", havingValue = "kafkaMq")
public class KafkaMqLogServiceImpl implements LogService {
    @Autowired
    private LogRecordProperties properties;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public boolean createLog(LogDTO logDTO) throws Exception {
        log.info("LogRecord KafkaMq ready to send topic [{}] LogDTO [{}]", properties.getKafkaMqProperties().getTopic(), logDTO);
        kafkaTemplate.send(properties.getKafkaMqProperties().getTopic(), JSON.toJSONString(logDTO));
        return true;
    }
}
