package com.financialprocessingsystem.account.service.Impl;

import com.financialprocessingsystem.account.dto.request.CreateAccountRequest;
import com.financialprocessingsystem.account.dto.response.AccountResponse;
import com.financialprocessingsystem.account.mapper.AccountMapper;
import com.financialprocessingsystem.account.model.Account;
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
import com.financialprocessingsystem.user.model.User;
import com.financialprocessingsystem.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final AccountMapper accountMapper;



    @Override
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {

        Role role = roleRepository.findByName(RoleName.ROLE_CUSTOMER)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + RoleName.ROLE_CUSTOMER.getDescription()));

        User user = userMapper.toUser(request.getUser());
        user.setRole(role);

        String iban = generateUniqueIban();

        Account account = accountMapper.mapToAccountEntity(iban, user);
        Address address = addressMapper.toAddressEntity(request.getAddress(), user);

        user.setAccount(account);
        user.setAddress(address);
        account.setUser(user);

        Account savedAccount = accountRepository.save(account);

        return accountMapper.mapToAccountResponse(savedAccount);
    }

    @Override
    public void lockAccount(Long accountId) {
        accountRepository.lockAccount(accountId);
        log.info("Account locked successfully");
    }

    @Override
    public void unlockAccount(Long accountId) {
        accountRepository.unlockAccount(accountId);
        log.info("Account unlocked successfully");
    }

    @Override
    public List<AccountResponse> findAllAccounts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return accountRepository.findAll(pageRequest)
                .getContent()
                .stream()
                .map(accountMapper::mapToAccountResponse)
                .toList();
    }

    @Override
    public AccountResponse findAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .map(accountMapper::mapToAccountResponse)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + accountId));
    }


    private String generateUniqueIban() {
        return Stream.generate(this::generateIban)
                .filter(iban -> !accountRepository.existsByIban(iban))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unique IBAN could not be generated"));
    }

    private String generateIban() {
        final String newIban = Iban.random(CountryCode.TN)
                .toFormattedString();

        boolean ibanAlreadyExists = accountRepository.existsByIban(newIban);
        if (ibanAlreadyExists) {
            generateIban();
        }
        return newIban;
    }

}
