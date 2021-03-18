package com.ewallet.entity.request;

import com.ewallet.entity.TransactionType;
import lombok.Data;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Data
public abstract class TransactionRequest {

    private TransactionType transactionType;
    private String phoneNo;
    private float amount;
    private String message;

}
