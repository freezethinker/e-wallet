package com.ewallet.repository;

import com.ewallet.entity.Wallet;
import com.ewallet.exception.InternalException;
import org.springframework.stereotype.Repository;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Repository
public interface WalletRepository {

    /**
     * Method to support addition of a wallet.
     *
     * @param wallet Wallet entity to be persisted.
     */
    void add(Wallet wallet);

    /**
     * Method to retrieve wallet details from wallet ID.
     *
     * @param id Wallet ID for which wallet is needed.
     * @return Wallet entity requested.
     * @throws InternalException Exception for internal failures.
     */
    Wallet getWalletByWalletId(String id) throws InternalException;

    /**
     * Method to retrieve wallet details from phone number.
     *
     * @param phoneNo Phone no for which wallet is needed.
     * @return Wallet entity requested.
     * @throws InternalException Exception for internal failures.
     */
    Wallet getWalletByPhoneNo(String phoneNo) throws InternalException;

    /**
     * Method to update wallet by wallet ID.
     *
     * @param id     The entity ID that has to be updated.
     * @param wallet The wallet details to be updated.
     */
    void updateWalletByWalletId(String id, Wallet wallet);
}
