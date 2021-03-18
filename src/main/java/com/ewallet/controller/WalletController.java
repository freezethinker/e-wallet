package com.ewallet.controller;

import com.ewallet.entity.Wallet;
import com.ewallet.entity.request.CreateWalletRequest;
import com.ewallet.entity.response.WalletStatusResponse;
import com.ewallet.exception.InternalException;
import com.ewallet.exception.ValidationError;
import com.ewallet.service.TransactionService;
import com.ewallet.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/wallet",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class WalletController extends BaseController {

    private final WalletService walletService;
    private final TransactionService transactionService;

    @Autowired
    public WalletController(WalletService walletService, TransactionService transactionService) {
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createWallet(@Valid @RequestBody CreateWalletRequest request) {
        log.info("Wallet create request received: {}", request.toString());
        try {
            Wallet wallet = walletService.createWallet(request);
            return new ResponseEntity<>(createSuccessResponse(wallet), HttpStatus.OK);
        } catch (ValidationError e) {
            return new ResponseEntity<>(createErrorResponse(e), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/status/{phoneNo}", method = RequestMethod.GET)
    public ResponseEntity<?> showWallet(@PathVariable(value = "phoneNo") String phoneNo) {
        log.info("Wallet status request received: {}", phoneNo);
        try {
            WalletStatusResponse walletStatusResponse = new WalletStatusResponse();
            walletStatusResponse.setWallet(walletService.getWalletByPhoneNo(phoneNo));
            walletStatusResponse.setTransactions(transactionService.getTransactionsByPhoneNo(phoneNo));
            return new ResponseEntity<>(createSuccessResponse(walletStatusResponse), HttpStatus.OK);
        } catch (InternalException e) {
            return new ResponseEntity<>(createErrorResponse(e), HttpStatus.BAD_REQUEST);
        }
    }

}
