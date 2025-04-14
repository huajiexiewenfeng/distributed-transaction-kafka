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

    public boolean updateAmount(Long txId, Long sellerId, Long buyerId, Integer amount) {
        log.info("txId:{} Amount updated to: {}", txId, amount);
        userMapper.updateAmountSold(sellerId, amount);
        userMapper.updateAmountBought(buyerId, amount);
        return true;
    }
}
