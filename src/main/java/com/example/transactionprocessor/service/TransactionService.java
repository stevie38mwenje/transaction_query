package com.example.transactionprocessor.service;

import com.example.transactionprocessor.dto.TransactionRequest;
import com.example.transactionprocessor.dto.UserRequest;
import com.example.transactionprocessor.model.Transactions;
import com.example.transactionprocessor.model.User;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    List<Transactions> getTransactions(Long userId);

    Transactions createTransactions(TransactionRequest transactionRequest);

    List<Transactions> getAllTransactions();

    User createUser(UserRequest userRequest);

    List<Transactions> findByDateBetweenAndUserId(Date from, Date to,Long userId);

    void deleteTransactionById(Long id);

    Transactions updateTransaction(Long id, TransactionRequest transactionRequest);


    double getUserBalance(Long id);
}
