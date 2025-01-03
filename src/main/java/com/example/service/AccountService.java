package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;

import antlr.collections.List;
import java.util.Optional;
import com.example.entity.Account;;



@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        
        List<Account> x =  accountRepository.findAll();
        return x;
        //fix lists ---------------------------------------------------------------------
    }

    public Account getAccountById(Integer id) {

        Optional<Account> optionalAccount = accountRepository.findById(id);
        Account x = optionalAccount.orElse(null);

        return x;
    }

    public Account saveAccount(Account account) {

        Account x = accountRepository.save(account);
        return x;
    }
    public Account updateAccount(Integer id, Account account) {

        if (accountRepository.existsById(id)) {

            Account x = accountRepository.save(account);
            return x;

        } else {

            return null;

        }

    }

    public void deleteAccountById (Integer id) {
        
        accountRepository.deleteById(id);

    }

    public Account getAccountByUsername (String username) {
        
        Account x = accountRepository.findByUsername(username);
        //fix-------------------------------------------------------------------------
        return x;
    }

    public Account authenticate(String username, String password) {
        
        Account account = getAccountByUsername((username));

        if (account != null && account.getPassword().equals(password)) {
            return account;
        } else {
            return null;
        }



    }


}
