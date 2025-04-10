package com.bank.manager_dashboard.bankservice.core.mapper;

import com.bank.manager_dashboard.bankservice.core.domain.AmountTransferDTO;
import com.bank.manager_dashboard.bankservice.core.domain.AmountTransferEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountTransactionMapper {


//    Transaction transactionDTOToTransaction(TransactionDTO transactionDTO);
//
//    TransactionDTO transactionToTransactionDTO(Transaction transaction);
//
//    List<TransactionDTO> toTransactionDTOList(List<Transaction> transactionList);
//
    AmountTransferEntity amountTransferDTOToAmountTransferEntity(AmountTransferDTO amountTransferDTO);

    AmountTransferDTO amountTransferEntityToAmountTransferDTO(AmountTransferEntity amountTransferEntity);

//    List<AmountTransferDTO> toDTOList(List<AmountTransferEntity> entityList);

}
