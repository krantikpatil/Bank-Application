package com.bank.manager_dashboard.bankservice.core.port;

import com.bank.manager_dashboard.bankservice.core.domain.AmountTransferEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AccountTransactionPort {



    boolean fetchAccount(String accountNumber);

    List<AmountTransferEntity> fetchTransactionEntity(String accountNumber, String lastThreeMonthAgo);

    AmountTransferEntity storeTransaction(AmountTransferEntity amountTransferEntity);
}
