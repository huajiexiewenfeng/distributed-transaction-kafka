package com.csdn.dt.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易服务示例
 */
@RestController
public class TransactionServiceSample {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceSample.class);

    /**
     * 处理接收到的消息
     *
     * @param message 接收到的消息
     */
    @KafkaListener(topics = "transactions", groupId = "transaction_group")
    public void onMessage(String message) {
        // 处理接收到的消息
        logger.info("Received message: {}", message);

        // 进行业务逻辑处理
        // ...

        // 返回结果
        logger.info("Processed message: {}", message);
    }


    @KafkaListener(topics = "transactions-event", groupId = "transaction_group")
    public void onEvent(TxEvent txEvent, Acknowledgment ack) {
        // 处理接收到的消息
        logger.info("Received message: {}", txEvent);
        ack.acknowledge();
    }

}
