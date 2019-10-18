package com.example.kafka.consumer;

import com.alibaba.fastjson.JSON;
import com.example.kafka.entity.FileEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Auther: likui
 * @Date: 2019/9/24 21:30
 * @Description:
 */
@Slf4j
@Component
public class KafkaReceive {

    @KafkaListener(groupId = "consumer-group-007", topics = "kafka.test007")
    public void listen(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        //判断是否NULL
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            //获取消息
            String message = (String) kafkaMessage.get();
            log.info("Receive： +++++++++++++++ Topic:" + topic);
            log.info("Receive： +++++++++++++++ Record:" + record);
            log.info("Receive： +++++++++++++++ Message:" + message);
            FileEntity fileEntity = JSON.toJavaObject(JSON.parseObject(message), FileEntity.class);
            log.info("taskId = " + fileEntity.getTaskId() + "mesg=" + fileEntity.getFileContent());
        }
    }
}
