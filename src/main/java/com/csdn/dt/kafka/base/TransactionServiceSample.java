package com.csdn.dt.kafka.base;

import com.csdn.dt.kafka.TxEvent;
import com.csdn.dt.kafka.service.TransactionService;
import com.csdn.dt.kafka.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.ApplicationEventPublisher;

import java.util.concurrent.TimeUnit;

/**
 * 交易服务示例
 */
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAutoConfiguration
@RestController
public class TransactionServiceSample {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceSample.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private KafkaTemplate<String, TxEvent> kafkaTemplate;

    @Value("${kafka.transactions.topic:transactions}")
    private String transactionsTopic;

    @Value("${kafka.transactions.timeout:5000}")
    private long transactionsTimeout;

    @GetMapping("/tx/{sellerId}/{buyerId}/{amount}")
    @Transactional
    public boolean tx(@PathVariable Long sellerId, @PathVariable Long buyerId, @PathVariable Integer amount) {
        Long txId = transactionService.addTransaction(sellerId, buyerId, amount);
        applicationEventPublisher.publishEvent(new TxEvent(this, txId, sellerId, buyerId, amount));
        return true;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void beforeCommit(TxEvent txEvent) throws Throwable {
        ListenableFuture<SendResult<String, TxEvent>> future = kafkaTemplate.send(transactionsTopic, txEvent);
        SendResult<String, TxEvent> result = future.get(transactionsTimeout, TimeUnit.MILLISECONDS);
        logger.info("{}", result);
    }

}
