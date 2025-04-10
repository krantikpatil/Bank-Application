package com.bank.manager_dashboard.bankservice.core.port;

import com.bank.manager_dashboard.bankservice.core.domain.AmountTransaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AccountTransactionPort {


     void saveTransaction(AmountTransaction transaction);

    boolean fetchAccount(String accountNumber);

    List<AmountTransaction> fetchTransactionEntity(String accountNumber, String lastThreeMonthAgo);

//    AmountTransaction storeTransaction(AmountTransaction amountTransferEntity);
}
