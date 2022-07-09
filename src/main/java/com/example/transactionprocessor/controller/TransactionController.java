package com.example.transactionprocessor.controller;

import com.example.transactionprocessor.dto.*;
import com.example.transactionprocessor.model.Transactions;
import com.example.transactionprocessor.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping(value = "/{userId}")
    List<Transactions> getTransactions(@PathVariable(name = "userId") Long userId)
    {
        return transactionService.getTransactions(userId);
    }


    @GetMapping("")
    ResponseEntity<Response> getTransactionsByDateBetweenAndId(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value="datefrom")     @DateTimeFormat(pattern="yyyy-MM-dd") Date datefrom,
            @RequestParam(value="dateto")     @DateTimeFormat(pattern="yyyy-MM-dd") Date dateto)
    {
        List<Transactions> transactions = null;
        transactions = transactionService.findByDateBetweenAndId(userId,datefrom, dateto);

//        if (userId!=null||datefrom != null ||dateto!=null) {
//            transactions = transactionService.findByDateBetweenAndId(userId,datefrom, dateto);
//        } else {
//            transactions = transactionService.getTransactions(userId);
//        }
        if (transactions.size() > 0) {
            return new ResponseEntity<>(new Response(ConstantsStatusCodes.success, "Transactions fetched successfully",
                    transactions, null, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(ConstantsStatusCodes.failed, "Failed to fetch transactions",
                    transactions, null, null), HttpStatus.OK);
        }
    }


    @GetMapping("/all")
    List<Transactions> getAllTransactions()
    {
        return transactionService.getAllTransactions();
    }

    @PostMapping(value = "user")
    ResponseEntity<?> createUser(@RequestBody UserRequest userRequest)
    {
        var new_user =  transactionService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new_user);
    }

    @PostMapping()
    ResponseEntity<?> createTransactions(@RequestBody TransactionRequest transactionRequest)
    {
        var new_transaction =  transactionService.createTransactions(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new_transaction);
    }
}
