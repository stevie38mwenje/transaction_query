package com.example.transactionprocessor.repository;

import com.example.transactionprocessor.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions,Long> {
    List<Transactions> findTransactionsByUserId(Long userId);
}
