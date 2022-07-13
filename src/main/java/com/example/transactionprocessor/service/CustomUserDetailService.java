package com.example.transactionprocessor.service;

//import com.example.transactionprocessor.dto.JwtRequest;
import com.example.transactionprocessor.dto.JwtRequest;
import com.example.transactionprocessor.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final static Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

    JwtRequest jwtRequest;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.transactionprocessor.model.User user =userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("user not found");
        }

        String password = (String)user.getPassword();
        logger.info("pasword: {}",password);


        return new User(user.getUsername(), "$2a$12$9pFaPhHMfZNMzUXiRYHKouDY39Va3HaGV8D07fpxThEHCs9/B4mH.", new ArrayList<>());
    }
}