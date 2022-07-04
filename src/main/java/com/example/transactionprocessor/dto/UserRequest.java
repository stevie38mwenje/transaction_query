package com.example.transactionprocessor.dto;


import com.example.transactionprocessor.model.Transactions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest implements Serializable {
    private String name;
    private Double balance;
    private List<Transactions> transactions;
}
