package com.bankapplication.bankservice.core.port;

import com.bankapplication.bankservice.core.domain.Account;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AccountServicePort {

    Account create(Account account);

    List<Account> findByPanNumber(String panNumber);

    Account findByAccountNumber(String accountNumber);

    Account saveDetails(Account account);

}
