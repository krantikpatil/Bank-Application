package com.bankapplication.bankservice.core.mapper;

import com.bankapplication.bankservice.core.domain.Account;
import com.bankapplication.bankservice.core.domain.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel ="spring")
public interface AccountMapper {

//    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO AccountToAccountDTO(Account account);

    Account AccountDTOToAccount(AccountDTO accountDTO);

}
