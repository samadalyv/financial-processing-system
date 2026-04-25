package com.financialprocessingsystem.account.service.Impl;

import com.financialprocessingsystem.account.dto.request.CreateAccountRequest;
import com.financialprocessingsystem.account.dto.response.AccountResponse;
import com.financialprocessingsystem.account.mapper.AccountMapper;
import com.financialprocessingsystem.account.repository.AccountRepository;
import com.financialprocessingsystem.account.service.AccountService;
import com.financialprocessingsystem.address.dto.AddressRequest;
import com.financialprocessingsystem.address.mapper.AddressMapper;
import com.financialprocessingsystem.address.model.Address;
import com.financialprocessingsystem.address.repository.AddressRepository;
import com.financialprocessingsystem.role.enums.RoleName;
import com.financialprocessingsystem.role.model.Role;
import com.financialprocessingsystem.role.repository.RoleRepository;
import com.financialprocessingsystem.user.dto.request.UserRequest;
import com.financialprocessingsystem.user.mapper.UserMapper;
import com.financialprocessingsystem.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final AccountMapper accountMapper;


    @Override
    public AccountResponse createAccount(CreateAccountRequest accountRequest) {
        UserRequest userRequest = accountRequest.getUser();
        var user = userMapper.toUser(userRequest);

        Role customRole = roleRepository.findByName(RoleName)
        return null;
    }




}
