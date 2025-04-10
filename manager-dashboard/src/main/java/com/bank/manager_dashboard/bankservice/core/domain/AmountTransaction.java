package com.bank.manager_dashboard.bankservice.core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class AmountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID transactionId;
    private String name;
    private String fromAccount;
    private String toAccount;
    private String ifscCode;
    private BigDecimal amount;
    private String transactionDate;
    @Version
    private Integer version = 1;
}
