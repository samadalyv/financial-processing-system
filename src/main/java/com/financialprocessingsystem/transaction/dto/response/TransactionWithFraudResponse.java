package com.financialprocessingsystem.transaction.dto.response;

import com.financialprocessingsystem.fraud.enums.FraudStatus;
import com.financialprocessingsystem.transaction.model.enums.TransactionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionWithFraudResponse {

    private Long transactionId;
    private String userFullName;
    private String transactionDescription;
    private String sourceIban;
    private String destinationIban;
    private BigDecimal amount;
    private TransactionStatus transactionStatus;
    private LocalDateTime transactionDate;
    private LocalDateTime transactionUpdatedAt;
    private FraudStatus fraudStatus;
    private LocalDateTime fraudDate;
    private LocalDateTime fraudUpdatedAt;

}
