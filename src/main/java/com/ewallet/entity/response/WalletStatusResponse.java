package com.ewallet.entity.response;

import com.ewallet.entity.Transaction;
import com.ewallet.entity.User;
import com.ewallet.entity.Wallet;
import lombok.Data;

import java.util.List;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Data
public class WalletStatusResponse {
    private Wallet wallet;
    private List<Transaction> transactions;
}
