package com.bank.manager_dashboard.bankservice.out.repository;

import com.bank.manager_dashboard.bankservice.core.domain.AmountTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<AmountTransferEntity, UUID> {

    @Query(value = "SELECT * FROM amount_transfer_entity t WHERE t.from_account = :from_account AND TO_DATE(t.transaction_date, 'YYYY-MM-DD') >= CURRENT_DATE - INTERVAL '3 months'", nativeQuery = true)
    List<AmountTransferEntity> fetchLastThreeMonthTransaction(@Param("from_account") String from_account);
    boolean existsByFromAccount(String accountNumber);

}
