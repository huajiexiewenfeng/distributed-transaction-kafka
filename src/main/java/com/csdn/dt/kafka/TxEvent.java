package com.csdn.dt.kafka;

import org.springframework.context.ApplicationEvent;

public class TxEvent extends ApplicationEvent {

    private final Long txId;

    private final Long sellerId;

    private final Long buyerId;

    private final Long amount;

    public TxEvent() {
        this("", null, null, null, null);
    }

    public TxEvent(Object source, Long txId, Long sellerId, Long buyerId, Long amount) {
        super(source);
        this.txId = txId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.amount = amount;
    }

    public Long getTxId() {
        return txId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public Long getAmount() {
        return amount;
    }
}