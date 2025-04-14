package com.csdn.dt.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/send/message/{message}")
    public boolean sendMessage(@PathVariable String message) {
        try {
            kafkaTemplate.send("transactions", message);
            return true;
        } catch (Exception e) {
            // 处理异常
            return false;
        }
    }

    @GetMapping("/send/event")
    public boolean sendUser() {
        try {
            TxEvent txEvent = new TxEvent(this, 1L, 2L, 3L, 100L);
            kafkaTemplate.send("transactions-event", txEvent);
            return true;
        } catch (Exception e) {
            // 处理异常
            return false;
        }
    }
}
