package com.financialprocessingsystem.transaction.dto.response;

import com.financialprocessingsystem.fraud.enums.FraudStatus;
import com.financialprocessingsystem.fraud.enums.FraudType;
import com.financialprocessingsystem.transaction.model.enums.TransactionType;
import lombok.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDateTime date;
    private TransactionStatus status;
    private TransactionType type;
    private String destinationIbn;
    private boolean hasFraud;
    private FraudStatus fraudStatus;
    private FraudType fraudType;
}
