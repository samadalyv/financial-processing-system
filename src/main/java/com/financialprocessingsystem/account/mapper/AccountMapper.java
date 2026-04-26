package com.financialprocessingsystem.account.mapper;

import com.financialprocessingsystem.account.dto.response.AccountResponse;
import com.financialprocessingsystem.account.model.Account;
import com.financialprocessingsystem.user.model.User;
import org.springframework.stereotype.Service;


@Service
public class AccountMapper {

    public Account mapToAccountEntity(String iban, User savedUser) {
        if (savedUser == null) {
            throw new NullPointerException("User should not be null");
        }
        if (iban == null || iban.isEmpty() || iban.trim().isEmpty()) {
            throw new NullPointerException("Iban should not be empty or null");
        }
        return Account.builder()
                .iban(iban)
                .user(savedUser)
                .locked(true)
                .build();
    }


    public AccountResponse mapToAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .userFirstName(account.getUser().getFirstName())
                .userLastName(account.getUser().getLastName())
                .userEmail(account.getUser().getEmail())
                .accountIban(account.getIban())
                .locked(account.isLocked())
                .build();
    }

}
