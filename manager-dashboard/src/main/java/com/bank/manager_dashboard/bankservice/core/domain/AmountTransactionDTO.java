package com.bank.manager_dashboard.bankservice.core.domain;

import jakarta.persistence.Version;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Component
public class AmountTransactionDTO {

    private UUID transactionId;
    private String name;
    private String fromAccount;
    private String toAccount;
    private String ifscCode;
    private BigDecimal amount;
    private String transactionDate;
}
