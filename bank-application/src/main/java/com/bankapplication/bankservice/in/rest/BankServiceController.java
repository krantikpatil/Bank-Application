package com.bankapplication.bankservice.in.rest;

import com.bankapplication.bankservice.core.domain.AccountDTO;
import com.bankapplication.bankservice.core.domain.AmountTransferDTO;
import com.bankapplication.bankservice.core.usecase.AccountService;
import com.bankapplication.bankservice.out.jpa.adapter.AccountRepositoryAdapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/banking-service/user")
public class BankServiceController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepositoryAdapter accountRepositoryAdapter;


    @PostMapping("/create-account")
    public ResponseEntity<String> openAccount(@RequestBody AccountDTO accountDTO){

        String accountCreated = accountService.createAccount(accountDTO);
        return ResponseEntity.ok(accountCreated);
    }


    @PostMapping("/transfer-amount")
    public ResponseEntity<String> transferAmount(@RequestBody AmountTransferDTO amountTransfer){

        String message = accountService.transferAmount(amountTransfer);
        return ResponseEntity.ok(message);
    }
}
