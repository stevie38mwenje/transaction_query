package com.example.transactionprocessor.controller;

import com.example.transactionprocessor.dto.JwtRequest;
import com.example.transactionprocessor.dto.JwtResponse;
import com.example.transactionprocessor.dto.TransactionRequest;
import com.example.transactionprocessor.dto.UserRequest;
import com.example.transactionprocessor.model.Transactions;
import com.example.transactionprocessor.service.TransactionService;
import com.example.transactionprocessor.service.UserService;
import com.example.transactionprocessor.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    TransactionService transactionService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponse(token);
    }

    @GetMapping(value = "transactions/{userId}")
    List<Transactions> getTransactions(@PathVariable(name = "userId") Long userId)
    {
        return transactionService.getTransactions(userId);
    }

    @GetMapping(value = "transactions")
    List<Transactions> getAllTransactions()
    {
        return transactionService.getAllTransactions();
    }

    @PostMapping(value = "user")
    ResponseEntity<?> createUser(@RequestBody UserRequest userRequest)
    {
        var new_user =  transactionService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new_user);
    }

    @PostMapping(value = "transactions")
    ResponseEntity<?> createTransactions(@RequestBody TransactionRequest transactionRequest)
    {
        var new_transaction =  transactionService.createTransactions(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new_transaction);
    }
}
