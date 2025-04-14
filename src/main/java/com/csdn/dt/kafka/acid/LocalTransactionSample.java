package com.csdn.dt.kafka.acid;

import com.csdn.dt.kafka.mapper.TransactionMapper;
import com.csdn.dt.kafka.service.TransactionService;
import com.csdn.dt.kafka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/*
 * 本地事务示例
 */
@EnableAutoConfiguration
@RestController
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackages = {"com.csdn.dt.kafka"})
public class LocalTransactionSample {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/tx/{sellerId}/{buyerId}/{amount}")
    @Transactional
    public boolean tx(@PathVariable("sellerId") Long sellerId,
                      @PathVariable("buyerId") Long buyerId,
                      @PathVariable("amount") Integer amount) {
        Long txId = transactionService.addTransaction(sellerId, buyerId, amount);
        System.out.println("txId: " + txId);
        // 更新用户表
        userService.updateAmount(txId, sellerId, buyerId, amount);
        eventPublisher.publishEvent(txId);
        return true;
    }


    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void beforeCommit(Object object) {
        System.out.println(object);
        // TODO
    }

    public static void main(String[] args) {
        // 启动Spring Boot应用
        SpringApplication.run(LocalTransactionSample.class, args);
    }
}
