package com.financialprocessingsystem.transaction.mapper;

import com.financialprocessingsystem.fraud.enums.FraudStatus;
import com.financialprocessingsystem.fraud.enums.FraudType;
import com.financialprocessingsystem.transaction.dto.request.TransactionDepositRequest;
import com.financialprocessingsystem.transaction.dto.request.TransactionTransferRequest;
import com.financialprocessingsystem.transaction.dto.request.TransactionWithdrawalRequest;
import com.financialprocessingsystem.transaction.dto.response.TransactionResponse;
import com.financialprocessingsystem.transaction.dto.response.TransactionWithFraudResponse;
import com.financialprocessingsystem.transaction.model.Transaction;
import com.financialprocessingsystem.transaction.model.enums.TransactionStatus;
import com.financialprocessingsystem.transaction.model.enums.TransactionType;
import com.financialprocessingsystem.user.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionMapper {
    public Transaction toTransactionEntity(TransactionDepositRequest request, Long userId) {
        return Transaction.builder()
                .date(LocalDateTime.now())
                .description("Deposing money")
                .amount(request.getDepositAmount())
                .type(TransactionType.DEPOSIT)
                .status(TransactionStatus.COMPLETED)
                .user(
                        User.builder()
                                .id(userId)
                                .build()
                )
                .build();
    }

    public Transaction toTransactionEntity(TransactionWithdrawalRequest request, Long userId) {
        return Transaction.builder()
                .date(LocalDateTime.now())
                .description("Withdrawing money")
                .amount(request.getWithdrawalAmount().multiply(BigDecimal.valueOf(-1)))
                .type(TransactionType.WITHDRAW)
                .status(TransactionStatus.COMPLETED)
                .user(
                        User.builder()
                                .id(userId)
                                .build()
                ).build();
    }

    public Transaction toTransactionEntity(TransactionTransferRequest request, Long userId) {
        return Transaction.builder()
                .date(LocalDateTime.now())
                .description(request.getRaison())
                .amount(request.getTransferAmount().multiply(BigDecimal.valueOf(-1)))
                .type(TransactionType.TRANSFER)
                .status(TransactionStatus.COMPLETED)
                .user(
                        User.builder()
                                .id(userId)
                                .build()
                ).build();
    }

    public TransactionResponse toTransactionResponse(Transaction transaction) {
        boolean hasFraud = transaction.getFraud() != null;
        FraudStatus status = null;
        FraudType type = null;
        if (hasFraud) {
            status = transaction.getFraud().getStatus();
            type = transaction.getFraud().getType();
        }
        return TransactionResponse.builder()
                .id(transaction.getId())
                .description(transaction.getDescription())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .destinationIbn(transaction.getDestinationIban())
                .type(transaction.getType())
                .status(transaction.getStatus())
                .hasFraud(hasFraud)
                .fraudStatus(status)
                .fraudType(type)
                .build();
    }

    public TransactionWithFraudResponse toTransactionWithFraudResponse(Transaction transaction) {
        return TransactionWithFraudResponse.builder()
                .transactionId(transaction.getId())
                .transactionDescription(transaction.getDescription())
                .userFullName(transaction.getUser().fullName())
                .sourceIban(transaction.getSourceIban())
                .destinationIban(transaction.getDestinationIban())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getDate())
                .transactionStatus(transaction.getStatus())
                .transactionUpdatedAt(transaction.getLastModifiedDate())
                .fraudStatus(transaction.getFraud().getStatus())
                .fraudDate(transaction.getFraud().getDate())
                .fraudUpdatedAt(transaction.getFraud().getLastModifiedDate())
                .build();
    }
}
