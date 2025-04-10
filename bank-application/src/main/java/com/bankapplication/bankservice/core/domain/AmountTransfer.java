package com.bankapplication.bankservice.core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class AmountTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID transactionId;
    private String name;
    private String fromAccount;
    private String toAccount;
    private String ifscCode;
    private BigDecimal amount;
    private String transactionDate;
}
