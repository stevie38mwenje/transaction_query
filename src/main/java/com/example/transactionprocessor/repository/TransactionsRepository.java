package com.example.transactionprocessor.repository;

import com.example.transactionprocessor.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions,Long> {
    List<Transactions> findTransactionsByUserId(Long userId);

//    @Query(value="SELECT id,amount,bank,mobile,name FROM Transactions  WHERE date between :from and :to",nativeQuery = true)
    List<Transactions> findByDateBetween(Date from, Date to);
}
