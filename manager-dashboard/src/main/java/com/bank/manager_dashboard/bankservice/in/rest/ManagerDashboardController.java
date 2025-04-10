package com.bank.manager_dashboard.bankservice.in.rest;

import com.bank.manager_dashboard.bankservice.core.domain.AmountTransactionDTO;
import com.bank.manager_dashboard.bankservice.core.usecase.TransactionService;
import com.bank.manager_dashboard.common.exception.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerDashboardController {

    @Autowired
    TransactionService transactionService;


    @GetMapping("/transactions/{accountNumber}")
    public ResponseEntity<List<AmountTransactionDTO>> fetchTransaction(@PathVariable String accountNumber){

         boolean isExist  =  transactionService.getUserDetails(accountNumber);

        if(!isExist){
            throw new AccountNotFoundException("User account not exist");
        }

       List<AmountTransactionDTO> reponseList =   transactionService.fetchTransaction(accountNumber);

        return ResponseEntity.ok(reponseList);
    }
}
