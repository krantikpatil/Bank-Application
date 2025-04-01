package com.bank.manager_dashboard.bankservice.core.mapper;

import com.bank.manager_dashboard.bankservice.core.domain.AmountTransferDTO;
import com.bank.manager_dashboard.bankservice.core.domain.AmountTransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountTransactionMapper {

    AccountTransactionMapper INSTANCE = Mappers.getMapper(AccountTransactionMapper.class);

    AmountTransferEntity amountTransferDTOToAmountTransferEntity(AmountTransferDTO amountTransferDTO);

    AmountTransferDTO amountTransferEntityToAmountTransferDTO(AmountTransferEntity amountTransferEntity);

    List<AmountTransferDTO> toDTOList(List<AmountTransferEntity> entityList);

}
