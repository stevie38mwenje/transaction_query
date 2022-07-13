package com.example.transactionprocessor.dto;

import com.example.transactionprocessor.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private String name;
    private String mobile;
    private String bank;
    private Date date;
    private Double amount;
    private User user;
}
