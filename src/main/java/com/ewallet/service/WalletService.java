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
public class WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void createWallet(CreateWalletRequest request) throws ValidationError {
        validateCreateWalletRequest(request);
        Wallet wallet = createWallet(createUser(request));
        walletRepository.addWallet(wallet);
    }

    public Wallet getWalletByPhoneNo(String phoneNo) throws InternalException {
        return walletRepository.getWalletByPhoneNo(phoneNo);
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
