package com.bankapplication.bankservice.core.domain;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class AccountDTO {

    private UUID id;
    private String name;
    private String accountNumber;
    private String ifscCode;
    private String accountType;
    private String panNumber;
    private String aadhaarNumber;
    private BigDecimal balance;
    private boolean isLocked;
}
