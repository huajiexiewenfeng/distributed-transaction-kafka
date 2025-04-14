package com.csdn.dt.kafka.base;

import com.csdn.dt.kafka.TxEvent;
import com.csdn.dt.kafka.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@EnableTransactionManagement(proxyTargetClass = true)
@EnableAutoConfiguration
@RestController
@Slf4j
public class UserServiceSample {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @KafkaListener(topics = "${kafka.transactions.topic:transactions}", groupId = "transaction_group")
    @Transactional
    public void onMessage(TxEvent txEvent, Acknowledgment ack) {
        // 处理接收到的消息
        log.info("Received message: {}", txEvent);
        userService.updateAmount(txEvent.getTxId(), txEvent.getSellerId(), txEvent.getBuyerId(), txEvent.getAmount());
        applicationEventPublisher.publishEvent(ack);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(Acknowledgment ack) throws Throwable {
        ack.acknowledge();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void afterRollback(Acknowledgment ack) {
        ack.nack(0);
    }
}
