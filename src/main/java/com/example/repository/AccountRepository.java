package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{

    // Named query to find an account by its username
    Account findAccountByUsername(String username);
    // Named query to find an account by username and password for login purpose
    Account findAccountByUsernameAndPassword(String username, String password);

}
