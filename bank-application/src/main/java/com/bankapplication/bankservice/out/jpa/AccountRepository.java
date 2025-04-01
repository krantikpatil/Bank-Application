package com.bankapplication.bankservice.out.jpa;

import com.bankapplication.bankservice.core.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findByPanNumber(String panNumber);

    Account findByAccountNumber(String accountNumber);
}
