package com.bank.manager_dashboard.bankservice.in.rest;

import com.bank.manager_dashboard.bankservice.core.domain.AmountTransferDTO;
import com.bank.manager_dashboard.bankservice.core.usecase.TransactionService;
import com.bank.manager_dashboard.bankservice.in.queue.TransactionConsumer;
import com.bank.manager_dashboard.common.exception.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerDashboardController {

    @Autowired
    TransactionConsumer transactionConsumer;

    @Autowired
    AmountTransferDTO amountTransferDTO;

    @Autowired
    TransactionService transactionService;



    @GetMapping("/transactions/{accountNumber}")
    public ResponseEntity<List<AmountTransferDTO>> fetchTransaction(@PathVariable String accountNumber){


         boolean isExist  =  transactionService.getUserDetails(accountNumber);

        if(!isExist){
            throw new AccountNotFoundException("User account not exist");
        }

       List<AmountTransferDTO> reponseList =   transactionService.fetchTransaction(accountNumber);


      System.out.println("Transactions from database:--");
      System.out.println(reponseList);

        return ResponseEntity.ok(reponseList);
    }
}
