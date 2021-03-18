package com.ewallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Data
public class Transaction {

    private String transactionId;
    private TransactionStatus status;
    private String walletId;
    private TransactionType transactionType;
    private float amount;
    private Metadata metadata;
    private LocalDateTime createdAt;

    @Data
    @AllArgsConstructor
    public static class Metadata {
        private String message;
    }

    public Transaction(String walletId, TransactionType transactionType, float amount, String message) {
        this.transactionId = UUID.randomUUID().toString();
        this.status = TransactionStatus.INITIATED;
        this.walletId = walletId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.metadata = new Metadata(message);
        this.createdAt = LocalDateTime.now();
    }
}
