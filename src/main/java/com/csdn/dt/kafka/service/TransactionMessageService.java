package com.csdn.dt.kafka.service;

import com.csdn.dt.kafka.mapper.TransactionMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionMessageService {

    @Autowired
    private TransactionMessageMapper transactionMessageMapper;

    public Long addTransactionMessage(Long txId, Long userId, Integer amount) {
        return transactionMessageMapper.addTransactionMessage(txId, userId, amount);
    }

    public boolean hasProcessedTransaction(Long txId, Long userId, Integer amount) {
        return transactionMessageMapper.getTransactionById(txId, userId, amount) > 0;
    }
}
