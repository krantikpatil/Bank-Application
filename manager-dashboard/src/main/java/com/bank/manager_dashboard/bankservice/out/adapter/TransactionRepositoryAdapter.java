package com.bank.manager_dashboard.bankservice.out.adapter;

import com.bank.manager_dashboard.bankservice.core.domain.AmountTransaction;
import com.bank.manager_dashboard.bankservice.core.port.AccountTransactionPort;
import com.bank.manager_dashboard.bankservice.out.repository.AmountTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionRepositoryAdapter implements AccountTransactionPort {


    @Autowired
    AmountTransactionRepository amountTransactionRepository;


    @Override
    public void saveTransaction(AmountTransaction transaction) {

        amountTransactionRepository.save(transaction);
    }

    @Override
    public boolean fetchAccount(String accountNumber) {

        return amountTransactionRepository.existsByFromAccount(accountNumber);
    }

    @Override
    public List<AmountTransaction> fetchTransactionEntity(String accountNumber, String lastThreeMonthAgo) {
        return amountTransactionRepository.fetchLastThreeMonthTransaction(accountNumber);
    }

//    public AmountTransaction storeTransaction(AmountTransaction amountTransferEntity) {
//
//        System.out.println("This is from adapter class:-- ");
//
//        System.out.println(amountTransferEntity);
//        return amountTransactionRepository.save(amountTransferEntity);
//    }


}
