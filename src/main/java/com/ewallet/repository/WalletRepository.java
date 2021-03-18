package com.ewallet.repository;

import com.ewallet.entity.Wallet;
import com.ewallet.exception.InternalException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Repository
public class WalletRepository {

    private HashMap<String, Wallet> walletIdtoWalletMap;
    private HashMap<String, String> phoneNumberToWalletIdMap;

    public WalletRepository() {
        this.walletIdtoWalletMap = new HashMap<>();
        this.phoneNumberToWalletIdMap = new HashMap<>();
    }

    public void addWallet(Wallet wallet) {
        walletIdtoWalletMap.put(wallet.getWalletId(), wallet);
        phoneNumberToWalletIdMap.put(wallet.getUser().getPhoneNo(), wallet.getWalletId());
    }

    public Wallet getWalletByWalletId(String id) throws InternalException {
        Wallet wallet = walletIdtoWalletMap.get(id);
        if (Objects.isNull(wallet))
            throw new InternalException("[getWalletByWalletId] No entity for id - " + id);
        return wallet;
    }

    public Wallet getWalletByPhoneNo(String phoneNo) throws InternalException {
        String walletId = phoneNumberToWalletIdMap.get(phoneNo);
        if (Objects.isNull(walletId)) {
            throw new InternalException("[getWalletByPhoneNo] No entity for phone number - " + phoneNo);
        }
        return getWalletByWalletId(walletId);

    }
}
