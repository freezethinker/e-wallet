package com.ewallet.controller;

import com.ewallet.entity.Transaction;
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

/**
 * Created by karan.uppal on 18/03/21
 **/
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/transact",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController extends BaseController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/debit", method = RequestMethod.POST)
    public ResponseEntity<?> debit(@RequestBody DebitRequest request) {
        log.info("Debit request received: {}", request.toString());
        try {
            request.setTransactionType(TransactionType.DEBIT);
            Transaction transaction = transactionService.debit(request);
            return new ResponseEntity<>(createSuccessResponse(transaction), HttpStatus.OK);
        } catch (ValidationError | InternalException e) {
            return new ResponseEntity<>(createErrorResponse(e), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/credit", method = RequestMethod.POST)
    public ResponseEntity<?> credit(@RequestBody CreditRequest request) {
        log.info("Credit request received: {}", request.toString());
        try {
            request.setTransactionType(TransactionType.CREDIT);
            Transaction transaction = transactionService.credit(request);
            return new ResponseEntity<>(createSuccessResponse(transaction), HttpStatus.OK);
        } catch (ValidationError | InternalException e) {
            return new ResponseEntity<>(createErrorResponse(e), HttpStatus.BAD_REQUEST);
        }
    }

}
