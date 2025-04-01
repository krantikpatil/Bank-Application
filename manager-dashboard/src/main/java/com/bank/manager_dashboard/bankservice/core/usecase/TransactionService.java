package com.bank.manager_dashboard.bankservice.core.usecase;

import com.bank.manager_dashboard.bankservice.core.domain.AmountTransferDTO;
import com.bank.manager_dashboard.bankservice.core.domain.AmountTransferEntity;
import com.bank.manager_dashboard.bankservice.core.mapper.AccountTransactionMapper;
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

    public boolean getUserDetails(String accountNumber){

      return accountTransactionPort.fetchAccount(accountNumber);
    }

    public List<AmountTransferDTO> fetchTransaction(String accountNumber){


        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        String formattedDate = threeMonthsAgo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

       List<AmountTransferEntity> amountList = accountTransactionPort.fetchTransactionEntity(accountNumber, formattedDate);



       return AccountTransactionMapper.INSTANCE.toDTOList(amountList);
    }

    public AmountTransferDTO saveTransaction(AmountTransferDTO amountTransferDTO){

       AmountTransferEntity amountTransferEntity = AccountTransactionMapper.INSTANCE.amountTransferDTOToAmountTransferEntity(amountTransferDTO);

        AmountTransferEntity amountTransferResponse =  accountTransactionPort.storeTransaction(amountTransferEntity);

         return AccountTransactionMapper.INSTANCE.amountTransferEntityToAmountTransferDTO(amountTransferResponse);
    }
}
