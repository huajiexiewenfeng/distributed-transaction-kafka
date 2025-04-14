package com.csdn.dt.kafka.service;

import com.csdn.dt.kafka.mapper.TransactionMapper;
import com.csdn.dt.kafka.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Transactional
    public Long addTransaction(Long sellerId, Long buyerId, Integer amount) {
        Transaction transaction = new Transaction();
        transaction.setSellerId(sellerId);
        transaction.setBuyerId(buyerId);
        transaction.setAmount(amount);
        transactionMapper.insertTransaction(transaction);
        return transaction.getXid();
    }


}
