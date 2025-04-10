package com.bank.manager_dashboard.bankservice.in.queue;

import com.bank.manager_dashboard.bankservice.core.domain.AmountTransaction;
import com.bank.manager_dashboard.bankservice.core.domain.AmountTransactionDTO;
import com.bank.manager_dashboard.bankservice.core.usecase.TransactionService;
import com.bank.manager_dashboard.bankservice.out.repository.AmountTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
@Slf4j
@Component
public class TransactionConsumer {

    @Autowired
    TransactionService transactionService;

    @Autowired
    AmountTransactionRepository amountTransactionRepository;

    @Autowired
    AmountTransactionDTO amountTransactionDTO;

    @Bean
    public Consumer<AmountTransaction> transactionListener(){

       return  transfer -> {

    AmountTransaction at =  AmountTransaction.builder().transactionId(transfer.getTransactionId())
                       .name(transfer.getName())
                               .fromAccount(transfer.getFromAccount())
                                       .toAccount(transfer.getToAccount())
                                               .amount(transfer.getAmount()).build();

           System.out.println("This is the consumer:--");

          AmountTransaction a = amountTransactionRepository.save(transfer);

          System.out.println(a);
//           transfer.setTransactionDate(String.valueOf(LocalDateTime.now()));
//           transfer.setTransactionId(null);
//
         //  System.out.println(transfer);
//           transactionService.saveTransactionDetails(transfer);

       };
   }
}
