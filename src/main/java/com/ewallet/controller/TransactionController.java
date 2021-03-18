package com.ewallet.controller;

import com.ewallet.entity.TransactionType;
import com.ewallet.entity.request.CreditRequest;
import com.ewallet.entity.request.DebitRequest;
import com.ewallet.exception.InternalException;
import com.ewallet.exception.ValidationError;
import com.ewallet.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/api/v1/transact",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/debit", method = RequestMethod.POST)
    public ResponseEntity<?> debit(@RequestBody DebitRequest request) {
        try {
            request.setTransactionType(TransactionType.DEBIT);
            transactionService.debit(request);
            return new ResponseEntity<>("Successful!", HttpStatus.OK);
        } catch (ValidationError | InternalException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/credit", method = RequestMethod.POST)
    public ResponseEntity<?> credit(@RequestBody CreditRequest request) {
        try {
            request.setTransactionType(TransactionType.CREDIT);
            transactionService.credit(request);
            return new ResponseEntity<>("Successful!", HttpStatus.OK);
        } catch (ValidationError | InternalException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
