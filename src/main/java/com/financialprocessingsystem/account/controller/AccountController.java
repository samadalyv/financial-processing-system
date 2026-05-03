package com.financialprocessingsystem.account.controller;

import com.financialprocessingsystem.account.dto.request.CreateAccountRequest;
import com.financialprocessingsystem.account.dto.response.AccountResponse;
import com.financialprocessingsystem.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@Valid @RequestBody CreateAccountRequest accountRequest) {
        accountService.createAccount(accountRequest);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAllAccounts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(accountService.findAllAccounts(page, size));
    }

    @GetMapping("/{account-id}")
    public ResponseEntity<AccountResponse> findAccountById(
            @PathVariable("account-id") Long accountId
    ) {
        return ResponseEntity.ok(accountService.findAccountById(accountId));
    }

    @PatchMapping("/lock/{account-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void lockAccount(
            @PathVariable("account-id") Long accountId
    ) {
        accountService.lockAccount(accountId);
    }

    @PatchMapping("/unlock/{account-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void unlockAccount(
            @PathVariable("account-id") Long accountId
    ) {
        accountService.unlockAccount(accountId);
    }
}
