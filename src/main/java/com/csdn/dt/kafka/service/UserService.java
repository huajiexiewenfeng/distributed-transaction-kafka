package com.csdn.dt.kafka.service;

import com.csdn.dt.kafka.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TransactionMessageService transactionMessageService;

    public boolean updateAmount(Long txId, Long sellerId, Long buyerId, Integer amount) {
        if (transactionMessageService.hasProcessedTransaction(txId, sellerId, amount)) {
            log.warn("The transaction[id :{}] for seller[id : {}] has been processed", txId, sellerId);
            return false;
        }
        log.info("txId:{} Amount updated to: {}", txId, amount);
        userMapper.updateAmountSold(sellerId, amount);
        userMapper.updateAmountBought(buyerId, amount);
        transactionMessageService.addTransactionMessage(txId, sellerId, amount);
        transactionMessageService.addTransactionMessage(txId, buyerId, amount);
        return true;
    }
}
