package com.bankapplication.bankservice.out.jpa.adapter;

import com.bankapplication.bankservice.core.domain.Account;
import com.bankapplication.bankservice.core.port.AccountServicePort;
import com.bankapplication.bankservice.out.jpa.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountRepositoryAdapter implements AccountServicePort {

    @Autowired
    AccountRepository accountRepository;

    public  void getAll(){
       List<Account> accounts = accountRepository.findAll();
    }


    @Override
    public Account create(Account account) {

        return accountRepository.save(account);
    }

    @Override
    public List<Account> findByPanNumber(String panNumber) {
        return accountRepository.findByPanNumber(panNumber);
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Account saveDetails(Account account) {
        return accountRepository.save(account);
    }
}
