package com.ewallet.repository;

import com.ewallet.entity.Transaction;
import com.ewallet.exception.InternalException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by karan.uppal on 18/03/21
 **/
public class TransactionRepositoryInMemory {

    private HashMap<String, Transaction> transactionIdToTransaction;
    private HashMap<String, List<String>> walletIdToTransactionIds;

    public TransactionRepositoryInMemory() {
        this.transactionIdToTransaction = new HashMap<>();
        this.walletIdToTransactionIds = new HashMap<>();
    }

    public void add(Transaction transaction) {
        transactionIdToTransaction.put(transaction.getTransactionId(), transaction);
        List<String> transactionIdsForWalletId = walletIdToTransactionIds.getOrDefault(transaction.getWalletId(), new ArrayList<>());
        transactionIdsForWalletId.add(transaction.getTransactionId());
        walletIdToTransactionIds.put(transaction.getWalletId(), transactionIdsForWalletId);
    }

    public Transaction getTransactionFromTransactionId(String transactionId) throws InternalException {
        Transaction transaction = transactionIdToTransaction.get(transactionId);
        if (Objects.isNull(transaction))
            throw new InternalException("[getTransactionFromTransactionId] No entity for id - " + transactionId);
        return transaction;
    }

    public List<Transaction> getTransactionsByWalletId(String walletId) throws InternalException {
        List<String> transactionIds = walletIdToTransactionIds.get(walletId);
        if (Objects.isNull(transactionIds))
            throw new InternalException("[getTransactionsByWalletId] No entities for id - " + walletId);
        List<Transaction> transactions = new ArrayList<>();
        for (String transactionId : transactionIds) {
            transactions.add(getTransactionFromTransactionId(transactionId));
        }
        return transactions;
    }
}
