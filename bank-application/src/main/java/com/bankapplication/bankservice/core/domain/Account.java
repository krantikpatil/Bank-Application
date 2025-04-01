package com.bankapplication.bankservice.core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @Column(unique = true)
    private String accountNumber;
    private String ifscCode;
    private String accountType;
    private String panNumber;
    private String aadhaarNumber;
    private BigDecimal balance;
    private boolean isLocked;
    @Version
    private Integer version = 0;
}
