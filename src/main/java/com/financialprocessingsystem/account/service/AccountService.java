package com.financialprocessingsystem.account.service;

import com.financialprocessingsystem.account.dto.request.CreateAccountRequest;
import com.financialprocessingsystem.account.dto.response.AccountResponse;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest accountRequest);
    void lockAccount(Long accountId);
    void unlockAccount(Long accountId);
    List<AccountResponse> findAllAccounts(int page, int size);
    AccountResponse findAccountById(Long accountId);
}
