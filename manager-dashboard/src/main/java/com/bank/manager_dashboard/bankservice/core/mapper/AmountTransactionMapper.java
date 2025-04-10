package com.bank.manager_dashboard.bankservice.core.mapper;

import com.bank.manager_dashboard.bankservice.core.domain.AmountTransaction;
import com.bank.manager_dashboard.bankservice.core.domain.AmountTransactionDTO;
import com.bank.manager_dashboard.bankservice.core.domain.AmountTransferDTO;
import com.bank.manager_dashboard.bankservice.core.domain.AmountTransferEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AmountTransactionMapper {

  AmountTransaction amountTransactionDTOToAmountTransaction(AmountTransactionDTO amountTransactionDTO);

  AmountTransactionDTO amountTransactionToAmountTransactionDTO(AmountTransaction amountTransaction);

  List<AmountTransactionDTO> toDTOList(List<AmountTransaction> entityList);

}
