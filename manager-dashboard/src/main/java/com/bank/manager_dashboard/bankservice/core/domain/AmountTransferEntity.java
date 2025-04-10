package com.bank.manager_dashboard.bankservice.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
@JsonIgnoreProperties(ignoreUnknown = true)

@Entity
public class AmountTransferEntity {

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
