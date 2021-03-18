package com.ewallet.service;

import com.ewallet.entity.Transaction;
import com.ewallet.entity.request.CreditRequest;
import com.ewallet.entity.request.DebitRequest;
import com.ewallet.exception.InternalException;
import com.ewallet.exception.ValidationError;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Service
public interface TransactionService {

    /**
     * Get all transactions for a customer.
     *
     * @param phoneNo Identifier for a customer.
     * @return List of transaction entity.
     * @throws InternalException Exception for internal failures.
     */
    List<Transaction> getTransactionsByPhoneNo(String phoneNo) throws InternalException;

    /**
     * To support a debit functionality for a wallet.
     *
     * @param request Request containing necessary info like amount, purpose, customer phone.
     * @return Transaction with reference number.
     * @throws ValidationError   Exception for validation failures.
     * @throws InternalException Exception for internal failures.
     */
    Transaction debit(DebitRequest request) throws ValidationError, InternalException;

    /**
     * To support a credit functionality for a wallet.
     *
     * @param request Request containing necessary info like amount, purpose, customer phone.
     * @return Transaction with reference number.
     * @throws ValidationError   Exception for validation failures.
     * @throws InternalException Exception for internal failures.
     */
    Transaction credit(CreditRequest request) throws ValidationError, InternalException;

}
