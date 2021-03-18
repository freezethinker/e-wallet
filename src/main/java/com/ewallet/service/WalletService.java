package com.ewallet.service;

import com.ewallet.entity.Wallet;
import com.ewallet.entity.request.CreateWalletRequest;
import com.ewallet.exception.InternalException;
import com.ewallet.exception.ValidationError;
import org.springframework.stereotype.Service;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Service
public interface WalletService {

    /**
     * Create a wallet for a request.
     *
     * @param request Contains the info to create a wallet.
     * @return The wallet entity for a user.
     * @throws ValidationError Exception for validation failures.
     */
    Wallet createWallet(CreateWalletRequest request) throws ValidationError;

    /**
     * Get a wallet as per the customer's phone number.
     *
     * @param phoneNo The string phone number.
     * @return The wallet entity for a user.
     * @throws InternalException Exception for internal failures.
     */
    Wallet getWalletByPhoneNo(String phoneNo) throws InternalException;

    /**
     * Method to define lock taking mechanism, can be a DB version locking or flag level locking (depends on implementation).
     *
     * @param walletId Wallet ID for lock acquisition.
     * @throws InternalException Exception for internal failures.
     */
    void acquireWriteLockOnWallet(String walletId) throws InternalException;

    /**
     * Method to define lock releasing mechanism.
     *
     * @param walletId Wallet ID for lock release.
     * @throws InternalException Exception for internal failures.
     */
    void releaseWriteLockOnWallet(String walletId) throws InternalException;
}
