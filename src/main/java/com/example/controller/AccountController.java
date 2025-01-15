package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.service.AccountService;

import com.example.service.*;

@RestController


public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {

        Account x = accountService.getAccountByUsername(account.getUsername());
        if (x != null) {

            return new ResponseEntity<>(HttpStatus.CONFLICT);

        }

        Account savedAccount = accountService.saveAccount(account);

        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        
        Account x = accountService.authenticate(account.getUsername(), account.getPassword());

        if (x != null) {

            return new ResponseEntity<> (x, HttpStatus.OK);

        } 

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    
}
