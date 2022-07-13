package com.example.transactionprocessor.controller;

import com.example.transactionprocessor.dto.*;
import com.example.transactionprocessor.model.Transactions;
import com.example.transactionprocessor.service.TransactionService;
import com.example.transactionprocessor.service.CustomUserDetailService;
import com.example.transactionprocessor.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    TransactionService transactionService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService userService;


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

    @GetMapping(value = "/{userId}")
    List<Transactions> getTransactions(@PathVariable(name = "userId") Long userId)
    {
        return transactionService.getTransactions(userId);
    }


    @GetMapping("/search")
    List<Transactions> getNotificationbyDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date datefrom,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateto,
            @RequestParam(required = true) Long userId
    ) {
        return transactionService.findByDateBetweenAndUserId(datefrom, dateto, userId);
    }


    @GetMapping()
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

    @PostMapping()
    ResponseEntity<?> createTransactions(@RequestBody TransactionRequest transactionRequest)
    {
        var new_transaction =  transactionService.createTransactions(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new_transaction);
    }

    @PutMapping("{id}")
    public Transactions updateTransaction(@PathVariable("id") Long id, @RequestBody TransactionRequest transactionRequest) {
        return transactionService.updateTransaction(id, transactionRequest);
    }


    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteTransaction(@PathVariable("id") Long id) {
        transactionService.deleteTransactionById(id);
        return new ResponseEntity<>(
                new Response(ConstantsStatusCodes.success, "transaction deleted successfully", null, null, null),
                HttpStatus.OK);
    }

    @GetMapping("/balance/{id}")
    ResponseEntity<Response> getUserbalance(@PathVariable("id") Long id) {
        double balance =transactionService.getUserBalance(id);
        return new ResponseEntity<>(new Response(ConstantsStatusCodes.success, "Balance fetched successful", null,
                null, String.valueOf(balance)), HttpStatus.OK);
    }

}
