package com.ewallet.service;

import com.ewallet.entity.Transaction;
import com.ewallet.entity.TransactionStatus;
import com.ewallet.entity.Wallet;
import com.ewallet.entity.request.CreditRequest;
import com.ewallet.entity.request.DebitRequest;
import com.ewallet.entity.request.TransactionRequest;
import com.ewallet.exception.InternalException;
import com.ewallet.exception.ValidationError;
import com.ewallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Service
public class TransactionService {

    private WalletService walletService;
    private TransactionRepository transactionRepository;

    @Value("#{T(java.lang.Float).parseFloat('${limits.min-wallet-balance}')}")
    private float minWalletBalance;

    @Value("#{T(java.lang.Float).parseFloat('${limits.max-debit}')}")
    private float maxDebitLimit;

    @Autowired
    public TransactionService(WalletService walletService, TransactionRepository transactionRepository) {
        this.walletService = walletService;
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactionByPhoneNo(String phoneNo) throws InternalException {
        Wallet wallet = walletService.getWalletByPhoneNo(phoneNo);
        return transactionRepository.getTransactionsByWalletId(wallet.getWalletId());
    }

    public Transaction debit(DebitRequest request) throws ValidationError, InternalException {
        validateDebitRequest(request);
        Wallet wallet = walletService.getWalletByPhoneNo(request.getPhoneNo());
        validateDebitTransaction(request, wallet);

        String transactionId = createTransaction(wallet, request);
        wallet.setBalance(wallet.getBalance() - request.getAmount());
        return updateTransaction(transactionId, TransactionStatus.COMPLETED);
    }

    public Transaction credit(CreditRequest request) throws ValidationError, InternalException {
        validateCreditRequest(request);
        Wallet wallet = walletService.getWalletByPhoneNo(request.getPhoneNo());

        String transactionId = createTransaction(wallet, request);
        wallet.setBalance(wallet.getBalance() + request.getAmount());
        return updateTransaction(transactionId, TransactionStatus.COMPLETED);
    }

    private Transaction updateTransaction(String transactionId, TransactionStatus status) throws InternalException {
        Transaction transaction = transactionRepository.getTransactionFromTransactionId(transactionId);
        transaction.setStatus(status);
        return transaction;
    }

    private String createTransaction(Wallet wallet, TransactionRequest request) {
        Transaction transaction = new Transaction(wallet.getWalletId(), request.getTransactionType(), request.getAmount(), request.getMessage());
        transactionRepository.add(transaction);
        return transaction.getTransactionId();
    }

    private void validateCreditRequest(CreditRequest request) throws ValidationError {
        if (request.getAmount() < 0)
            throw new ValidationError("Credit amount incorrect - INR" + request.getAmount());
    }

    private void validateDebitTransaction(DebitRequest request, Wallet wallet) throws ValidationError {
        float newBalance = wallet.getBalance() - request.getAmount();
        if (newBalance < 0 || newBalance < minWalletBalance)
            throw new ValidationError("Minimum wallet balance to maintain - INR" + minWalletBalance);
    }

    private void validateDebitRequest(DebitRequest request) throws ValidationError {
        if (request.getAmount() < 0)
            throw new ValidationError("Debit amount incorrect - INR" + request.getAmount());
        if (maxDebitLimit < request.getAmount())
            throw new ValidationError("Maximum debit limit is - INR" + maxDebitLimit);
    }
}
