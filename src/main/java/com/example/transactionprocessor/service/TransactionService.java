package com.example.transactionprocessor.service;

import com.example.transactionprocessor.dto.TransactionRequest;
import com.example.transactionprocessor.dto.UserRequest;
import com.example.transactionprocessor.model.Transactions;
import com.example.transactionprocessor.model.Users;

import java.util.List;

public interface TransactionService {
    List<Transactions> getTransactions(Long userId);

    Transactions createTransactions(TransactionRequest transactionRequest);

    List<Transactions> getAllTransactions();

    Users createUser(UserRequest userRequest);
}