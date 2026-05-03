package com.financialprocessingsystem.transaction.dto;

import com.financialprocessingsystem.fraud.enums.FraudStatus;
import com.financialprocessingsystem.transaction.model.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransactionWithFraudProjection {

    Long getTransactionId();
    String getUserFullName();
    String getTransactionDescription();
    String getSourceIban();
    String getDestinationIban();
    BigDecimal getAmount();
    TransactionStatus getTransactionStatus();
    LocalDateTime getTransactionDate();
    LocalDateTime getTransactionUpdatedAt();
    FraudStatus getFraudStatus();
    LocalDateTime getFraudDate();
    LocalDateTime getFraudUpdatedAt();

}
