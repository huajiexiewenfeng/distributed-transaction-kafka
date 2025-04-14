package com.csdn.dt.kafka.model;

import lombok.Data;

@Data
public class Transaction {
    private Long xid;
    private Long sellerId;
    private Long buyerId;
    private Integer amount;
}
