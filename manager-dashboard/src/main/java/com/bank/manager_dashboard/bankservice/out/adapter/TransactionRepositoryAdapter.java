package com.bank.manager_dashboard.bankservice.out.adapter;

import com.bank.manager_dashboard.bankservice.core.domain.AmountTransferEntity;
import com.bank.manager_dashboard.bankservice.core.port.AccountTransactionPort;
import com.bank.manager_dashboard.bankservice.out.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionRepositoryAdapter implements AccountTransactionPort {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public boolean fetchAccount(String accountNumber) {

        return transactionRepository.existsByFromAccount( accountNumber);
    }

    @Override
    public List<AmountTransferEntity> fetchTransactionEntity(String accountNumber, String lastThreeMonthAgo) {
        return transactionRepository.fetchLastThreeMonthTransaction(accountNumber);
    }

    @Override
    public AmountTransferEntity storeTransaction(AmountTransferEntity amountTransferEntity) {
        return transactionRepository.save(amountTransferEntity);
    }
}
