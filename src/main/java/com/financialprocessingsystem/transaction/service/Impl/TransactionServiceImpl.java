package com.financialprocessingsystem.transaction.service.Impl;

import com.financialprocessingsystem.contact.service.ContactService;
import com.financialprocessingsystem.exception.TransactionException;
import com.financialprocessingsystem.fraud.enums.FraudStatus;
import com.financialprocessingsystem.fraud.enums.FraudType;
import com.financialprocessingsystem.fraud.model.Fraud;
import com.financialprocessingsystem.fraud.repository.FraudRepository;
import com.financialprocessingsystem.transaction.dto.TransactionWithFraudProjection;
import com.financialprocessingsystem.transaction.dto.request.TransactionDepositRequest;
import com.financialprocessingsystem.transaction.dto.request.TransactionTransferRequest;
import com.financialprocessingsystem.transaction.dto.request.TransactionWithdrawalRequest;
import com.financialprocessingsystem.transaction.dto.response.TransactionResponse;
import com.financialprocessingsystem.transaction.dto.response.TransactionWithFraudResponse;
import com.financialprocessingsystem.transaction.mapper.TransactionMapper;
import com.financialprocessingsystem.transaction.model.Transaction;
import com.financialprocessingsystem.transaction.model.enums.TransactionStatus;
import com.financialprocessingsystem.transaction.repository.TransactionRepository;
import com.financialprocessingsystem.transaction.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final FraudRepository fraudRepository;
    private final ContactService contactService;

    @Override
    public void deposit(Long userId, TransactionDepositRequest request) {

        Transaction transaction = transactionMapper.toTransactionEntity(request, userId);
        transactionRepository.save(transaction);

    }

    @Override
    public void withdraw(Long userId, TransactionWithdrawalRequest request) {

        BigDecimal accountBalance = transactionRepository.calculateAccountBalance(userId);

        Transaction withdrwalTransaction = transactionMapper.toTransactionEntity(request, userId);

        if (accountBalance.compareTo(request.getWithdrawalAmount()) < 0) {

            throw new TransactionException("Withdrawal amount exceeds account balance");
        }

        transactionRepository.save(withdrwalTransaction);
    }

    @Override
    @Transactional
    public void transfer(Long userId, TransactionTransferRequest request) {
        String sourceIban = request.getSourceIban();
        String destinationIban = request.getDestinationIban();

        if (sourceIban.equals(destinationIban)) {
            throw new TransactionException("You cannot send money to yourself");
        }

        BigDecimal accountBalance = transactionRepository.calculateAccountBalance(userId);
        if (accountBalance.compareTo(request.getTransferAmount()) < 0) {

            throw new TransactionException("Insufficient funds");
        }

        if (!contactService.accountExists(destinationIban, userId)) {
            contactService.addContact(request.getContact(), userId);
        }

        Transaction transferTransaction = transactionMapper.toTransactionEntity(request, userId);

        boolean fraudHasBeenDetected = isFraudTransfer(accountBalance, request.getTransferAmount());
        if (fraudHasBeenDetected) {
            transferTransaction.setStatus(TransactionStatus.PENDING);
        }
        Transaction savedTransaction = transactionRepository.save(transferTransaction);

        if (fraudHasBeenDetected) {
            Fraud fraud = Fraud.builder()
                    .transaction(savedTransaction)
                    .date(LocalDateTime.now())
                    .type(FraudType.MONEY_LAUNDERING)
                    .status(FraudStatus.UNDER_INVESTIGATION)
                    .build();

            fraudRepository.save(fraud);
        }
    }

    @Override
    @Transactional
    public List<TransactionResponse> finaAllTransactions(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return transactionRepository.findAllByUserId(userId, pageRequest)
                .getContent()
                .stream()
                .map(transactionMapper::toTransactionResponse)
                .toList();
    }

    @Override
    @Transactional
    public List<TransactionWithFraudResponse> findAllTransactionsWithFraud(int page, int size, FraudType type) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return transactionRepository.findAllTransactionsHavingFraud(type, pageRequest)
                .getContent()
                .stream()
                .map(transactionMapper::toTransactionWithFraudResponse)
                .toList();
    }

    public List<TransactionWithFraudProjection> findAllByProj(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return transactionRepository.findAllTransactionsHavingFraudProj(FraudType.PHISHING, pageRequest)
                .getContent();
    }

    @Override
    @Transactional
    public void changeTransactionFraudStatus(Long transactionId, FraudStatus fraudStatus) {
        if (FraudStatus.UNDER_INVESTIGATION.equals(fraudStatus)) {
            throw new TransactionException("Unsupported status");
        }
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
        Fraud fraud = transaction.getFraud();

        fraud.setStatus(fraudStatus);
        if (FraudStatus.CONFIRMED.equals(fraudStatus)) {
            transaction.setStatus(TransactionStatus.CANCELLED);
        } else if (FraudStatus.REJECTED.equals(fraudStatus)) {
            transaction.setStatus(TransactionStatus.COMPLETED);
        }
        transactionRepository.save(transaction);
        fraudRepository.save(fraud);

    }

    private boolean isFraudTransfer(BigDecimal accountBalance, BigDecimal transferAmount) {
        boolean isGreaterThat5000 = transferAmount.compareTo(BigDecimal.valueOf(5000)) > 0;
        BigDecimal accountBalance40Percent = accountBalance.multiply(BigDecimal.valueOf(0.4));
        boolean isGreaterThan40Percent = transferAmount.compareTo(accountBalance40Percent) > 0;

        return isGreaterThan40Percent || isGreaterThat5000;
    }

}
