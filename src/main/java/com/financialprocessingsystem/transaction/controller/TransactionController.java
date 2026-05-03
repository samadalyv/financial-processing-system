package com.financialprocessingsystem.transaction.controller;

import com.financialprocessingsystem.fraud.enums.FraudStatus;
import com.financialprocessingsystem.fraud.enums.FraudType;
import com.financialprocessingsystem.transaction.dto.request.TransactionDepositRequest;
import com.financialprocessingsystem.transaction.dto.request.TransactionTransferRequest;
import com.financialprocessingsystem.transaction.dto.request.TransactionWithdrawalRequest;
import com.financialprocessingsystem.transaction.dto.response.TransactionResponse;
import com.financialprocessingsystem.transaction.dto.response.TransactionWithFraudResponse;
import com.financialprocessingsystem.transaction.service.TransactionService;
import com.financialprocessingsystem.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    public void deposit(
            @Valid @RequestBody TransactionDepositRequest request,
            Authentication connectedUser
    ) {
        final long userId = ((User)connectedUser.getPrincipal()).getId();
        transactionService.deposit(userId, request);
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void withdraw(
            @Valid @RequestBody TransactionWithdrawalRequest request,
            Authentication connectedUser
    ) {
        final long userId = ((User)connectedUser.getPrincipal()).getId();
        transactionService.withdraw(userId, request);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void transfer(
            @Valid @RequestBody TransactionTransferRequest request,
            Authentication connectedUser
    ) {
        final long userId = ((User)connectedUser.getPrincipal()).getId();
        transactionService.transfer(userId, request);
    }

    @GetMapping()
    public ResponseEntity<List<TransactionResponse>> findAllTransactionsByUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Authentication connectedUser
    ) {
        final long userId = ((User)connectedUser.getPrincipal()).getId();
        List<TransactionResponse> transactions = transactionService.finaAllTransactions(userId, page, size);
        return ResponseEntity.ok(transactions);

    }

    @GetMapping("/fraud")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionWithFraudResponse>> findAllTransactionsWithFraud(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam FraudType type
    ) {
        List<TransactionWithFraudResponse> fraudTransactions = transactionService.findAllTransactionsWithFraud(page, size, type);
        return ResponseEntity.ok(fraudTransactions);
    }

    @PatchMapping("/{transaction-id}/fraud-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeTransactionFraudStatus(
            @PathVariable("transaction-id") Long transactionId,
            @RequestParam FraudStatus fraudStatus) {
        transactionService.changeTransactionFraudStatus(transactionId, fraudStatus);
        return ResponseEntity.ok("Fraud status updated successfully.");
    }

}
