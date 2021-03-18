package com.ewallet.repository;

import com.ewallet.entity.Transaction;
import com.ewallet.exception.InternalException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Repository
public interface TransactionRepository {

    /**
     * Method to support addition of a transaction.
     *
     * @param transaction Transaction entity to be persisted.
     */
    void add(Transaction transaction);

    /**
     * Method to retrieve transaction details from transaction ID.
     *
     * @param transactionId The transaction ID for which the details are required.
     * @return Transaction entity requested.
     * @throws InternalException Exception for internal failures.
     */
    Transaction getTransactionFromTransactionId(String transactionId) throws InternalException;

    /**
     * Method to retrieve all transaction for a wallet.
     *
     * @param walletId Wallet ID for which transactions are needed.
     * @return Transactions entity requested.
     * @throws InternalException Exception for internal failures.
     */
    List<Transaction> getTransactionsByWalletId(String walletId) throws InternalException;

}
