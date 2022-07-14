package com.example.transactionprocessor.service;

import com.example.transactionprocessor.dto.TransactionRequest;
import com.example.transactionprocessor.dto.UserRequest;
import com.example.transactionprocessor.exception.CustomException;
import com.example.transactionprocessor.exception.UserNotFoundException;
import com.example.transactionprocessor.model.Transactions;
import com.example.transactionprocessor.model.User;
import com.example.transactionprocessor.repository.TransactionsRepository;
import com.example.transactionprocessor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final TransactionsRepository transactionsRepository;
    private final UserRepository userRepository;
    private final static Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());



    @Override
    public List<Transactions> getTransactions(Long user_id) {
        var user = userRepository.findById(user_id);
        if(user.isPresent()){
            var transactions = transactionsRepository.findTransactionsByUserId(user_id);
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

        var amount  = transaction.getAmount();
        var user = transaction.getUser();
        updateBalance(user,amount);

        return transactionsRepository.save(transaction);
    }

    @Override
    public List<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    @Override
    public User createUser(UserRequest userRequest) {
        logger.info("Persisting user data: {}", userRequest);
       User user = new User();
        user.setName(userRequest.getName());
        user.setBalance(userRequest.getBalance());
        user.setTransactions(userRequest.getTransactions());
        return userRepository.save(user);
    }

    @Override
    public List<Transactions> findByDateBetweenAndUserId(Date from, Date to, Long userId) {
        var id = userRepository.findById(userId);
        if(id.isPresent()){
            return transactionsRepository.findByDateBetweenAndUserId(from, to,userId);
        }
        throw new UserNotFoundException("User not found");
    }



    @Override
    public Transactions updateTransaction(Long id, TransactionRequest transactionRequest) {
        Optional<Transactions> tran = transactionsRepository.findById(id);
        if (tran.isPresent()) {
            Transactions transaction = new Transactions();
            transaction.setAmount(transactionRequest.getAmount());
            transaction.setBank(transactionRequest.getBank());
            transaction.setDate(date);
            transaction.setMobile(transactionRequest.getMobile());
            transaction.setName(transactionRequest.getName());
            transaction.setUser(transactionRequest.getUser());
            return transactionsRepository.save(transaction);
        } else {
            throw new CustomException("transaction not found");
        }
    }

    @Override
    public double getUserBalance(Long id) {
        var user = userRepository.findById(id);
        logger.info("user",user);
        if(user.isPresent()){
            var balance = user.get().getBalance();
            return balance;
        }
        throw new UserNotFoundException("user not found");
    }


    @Override
    public Optional<Transactions> deleteTransactionById(Long id) throws CustomException {
        Optional<Transactions> transaction = transactionsRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new CustomException("Transaction not found");
        }
        transactionsRepository.deleteById(id);
        return transaction;
    }

    private double updateBalance(User user, Double amount){
        Transactions transaction = new Transactions();
        double balance =user.getBalance();
        balance +=transaction.getAmount();
        logger.info("Updating user balance: {}",balance);
        return balance;
    }

}
