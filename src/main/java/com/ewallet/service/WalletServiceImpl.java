package com.ewallet.service;

import com.ewallet.entity.User;
import com.ewallet.entity.Wallet;
import com.ewallet.entity.request.CreateWalletRequest;
import com.ewallet.exception.InternalException;
import com.ewallet.exception.ValidationError;
import com.ewallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletRequest request) throws ValidationError {
        validateCreateWalletRequest(request);
        Wallet wallet = createWallet(createUser(request));
        walletRepository.add(wallet);
        return wallet;
    }

    public Wallet getWalletByPhoneNo(String phoneNo) throws InternalException {
        return walletRepository.getWalletByPhoneNo(phoneNo);
    }

    public void acquireWriteLockOnWallet(String walletId) throws InternalException {
        Wallet wallet = walletRepository.getWalletByWalletId(walletId);
        wallet.setInTransaction(true);
        walletRepository.updateWalletByWalletId(walletId, wallet);
    }

    public void releaseWriteLockOnWallet(String walletId) throws InternalException {
        Wallet wallet = walletRepository.getWalletByWalletId(walletId);
        wallet.setInTransaction(false);
        walletRepository.updateWalletByWalletId(walletId, wallet);
    }

    private Wallet createWallet(User user) {
        return new Wallet(user);
    }

    private User createUser(CreateWalletRequest request) {
        return new User(request.getFirstName(), request.getLastName(), request.getPhoneNo());
    }

    private void validateCreateWalletRequest(CreateWalletRequest request) throws ValidationError {
        if (request.getPhoneNo().length() != 10) {
            throw new ValidationError("Mobile number should be 10 digits long");
        }
        if (request.getFirstName().length() < 3) {
            throw new ValidationError("Please enter a valid First Name");
        }
    }
}
