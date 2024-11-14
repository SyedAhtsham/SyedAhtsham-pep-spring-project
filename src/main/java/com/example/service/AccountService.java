package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {


    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account){
        if(accountRepository.findAccountByUsername(account.getUsername())!=null){
            return null;
        }
        return accountRepository.save(account);
    }

    public Account loginAccount(Account account){
        return accountRepository.findAccountByUsernameAndPassword(account.getUsername(),account.getPassword());
    }
}
