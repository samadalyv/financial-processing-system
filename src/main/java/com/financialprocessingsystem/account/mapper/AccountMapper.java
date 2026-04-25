package com.financialprocessingsystem.account.mapper;

import com.financialprocessingsystem.account.dto.request.CreateAccountRequest;
import com.financialprocessingsystem.account.dto.response.AccountResponse;
import com.financialprocessingsystem.account.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toEntity(CreateAccountRequest accountRequest);
    AccountResponse toResponse(Account account);

}
