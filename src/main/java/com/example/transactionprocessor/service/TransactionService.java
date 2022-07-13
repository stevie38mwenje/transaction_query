package com.example.transactionprocessor.service;

import com.example.transactionprocessor.dto.TransactionRequest;
import com.example.transactionprocessor.dto.UserRequest;
import com.example.transactionprocessor.model.Transactions;
import com.example.transactionprocessor.model.Users;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TransactionService {
    List<Transactions> getTransactions(Long userId);

    Transactions createTransactions(TransactionRequest transactionRequest);

    List<Transactions> getAllTransactions();

    Users createUser(UserRequest userRequest);

    List<Transactions> findByDateBetweenAndUserId(Date from, Date to,Long userId);

    void deleteTransactionById(Long id);

    Transactions updateTransaction(Long id, TransactionRequest transactionRequest);
}
