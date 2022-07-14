package com.example.transactionprocessor.service;

import com.example.transactionprocessor.dto.TransactionRequest;
import com.example.transactionprocessor.dto.UserRequest;
import com.example.transactionprocessor.model.Transactions;
import com.example.transactionprocessor.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    List<Transactions> getTransactions(Long userId);

    Transactions createTransactions(Transactions transactionRequest);

    List<Transactions> getAllTransactions();

    User createUser(UserRequest userRequest);

    List<Transactions> findByDateBetweenAndUserId(Date from, Date to,Long userId);


    Transactions updateTransaction(Long id, Transactions transactionRequest);


    double getUserBalance(Long id);

    Optional<Transactions> deleteTransactionById(Long id);
}
