package com.example.transactionprocessor.service;

import com.example.transactionprocessor.dto.ConstantsStatusCodes;
import com.example.transactionprocessor.dto.Response;
import com.example.transactionprocessor.dto.TransactionRequest;
import com.example.transactionprocessor.dto.UserRequest;
import com.example.transactionprocessor.exception.UserNotFoundException;
import com.example.transactionprocessor.model.Transactions;
import com.example.transactionprocessor.model.Users;
import com.example.transactionprocessor.repository.TransactionsRepository;
import com.example.transactionprocessor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final TransactionsRepository transactionsRepository;
    private final UserRepository userRepository;
    private final static Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());



    @Override
    public List<Transactions> getTransactions(Long userId) {
        var user = userRepository.findById(userId);
        if(user.isPresent()){
            var transactions = transactionsRepository.findTransactionsByUserId(userId);
            return transactions;
        }
        else throw new UserNotFoundException("User not found") ;
    }

    @Override
    public Transactions createTransactions(TransactionRequest transactionRequest) {
        logger.info("Persisting transaction data: {}", transactionRequest);
        Transactions transaction = new Transactions();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setBank(transactionRequest.getBank());
        transaction.setDate(date);
        transaction.setMobile(transactionRequest.getMobile());
        transaction.setName(transactionRequest.getName());
        transaction.setUser(transactionRequest.getUser());
        logger.info("Persisting transaction data: {}", transaction);
        return transactionsRepository.save(transaction);
    }

    @Override
    public List<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    @Override
    public Users createUser(UserRequest userRequest) {
        logger.info("Persisting user data: {}", userRequest);
       Users user = new Users();
        user.setName(userRequest.getName());
        user.setBalance(userRequest.getBalance());
        user.setTransactions(userRequest.getTransactions());
        return userRepository.save(user);
    }

    @Override
    public List<Transactions> findByDateBetweenAndId(Date from, Date to, Long userId) {
        var id = userRepository.findById(userId);
        if(id.isPresent()){
            List<Transactions> transactions = null;
            if (from != null && to != null) {
                transactions = transactionsRepository.findByDateBetweenAndUserId(from, to, userId);
            } else {
                transactions = transactionsRepository.findTransactionsByUserId(userId);
            }
            return transactions;
        }
        throw new UserNotFoundException("User not found");
    }




}
