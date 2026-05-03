package com.financialprocessingsystem.transaction.service;

import com.financialprocessingsystem.fraud.enums.FraudStatus;
import com.financialprocessingsystem.fraud.enums.FraudType;
import com.financialprocessingsystem.transaction.dto.request.TransactionDepositRequest;
import com.financialprocessingsystem.transaction.dto.request.TransactionTransferRequest;
import com.financialprocessingsystem.transaction.dto.request.TransactionWithdrawalRequest;
import com.financialprocessingsystem.transaction.dto.response.TransactionResponse;
import com.financialprocessingsystem.transaction.dto.response.TransactionWithFraudResponse;

import java.util.List;

public interface TransactionService {
    void deposit(Long userId, TransactionDepositRequest request);
    void withdraw(Long userId, TransactionWithdrawalRequest request);
    void transfer(Long userId, TransactionTransferRequest request);
    List<TransactionResponse> finaAllTransactions(Long userId, int page, int size);
    List<TransactionWithFraudResponse> findAllTransactionsWithFraud(int page, int size, FraudType type);
    void changeTransactionFraudStatus(Long transactionId, FraudStatus fraudStatus);
}
