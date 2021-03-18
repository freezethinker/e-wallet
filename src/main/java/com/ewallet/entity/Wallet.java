package com.ewallet.entity;

import lombok.Data;

import java.util.UUID;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Data
public class Wallet {
    private String walletId;
    private User user;
    private float balance;

    public Wallet(User user) {
        this.walletId = UUID.randomUUID().toString();
        this.user = user;
        this.balance = 0;
    }
}
