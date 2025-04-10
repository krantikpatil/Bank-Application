package com.bank.manager_dashboard.bankservice.core.usecase;

import com.bank.manager_dashboard.bankservice.core.domain.*;
import com.bank.manager_dashboard.bankservice.core.mapper.AccountTransactionMapper;
import com.bank.manager_dashboard.bankservice.core.mapper.AmountTransactionMapper;
import com.bank.manager_dashboard.bankservice.core.port.AccountTransactionPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    AccountTransactionPort accountTransactionPort;

    @Autowired
    AmountTransactionMapper amountTransactionMapper;


    public boolean getUserDetails(String accountNumber){

      return accountTransactionPort.fetchAccount(accountNumber);
    }

    public List<AmountTransactionDTO> fetchTransaction(String accountNumber){


        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        String formattedDate = threeMonthsAgo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

       List<AmountTransaction> transactionList = accountTransactionPort.fetchTransactionEntity(accountNumber, formattedDate);

       return amountTransactionMapper.toDTOList(transactionList);
    }

    public void saveTransactionDetails(AmountTransactionDTO amountTransactionDTO){

      AmountTransaction amountTransaction =  amountTransactionMapper.amountTransactionDTOToAmountTransaction(amountTransactionDTO);

        accountTransactionPort.saveTransaction(amountTransaction);

    }

}
