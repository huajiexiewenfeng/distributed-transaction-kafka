package com.csdn.dt.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * 事件监听者示例启动类
 */
@SpringBootApplication
@EnableKafka
public class TrKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrKafkaApplication.class, args);
    }

}