package com.financialprocessingsystem.user.service.Impl;

import com.financialprocessingsystem.user.dto.request.UserRequest;
import com.financialprocessingsystem.user.dto.request.UserUpdateRequest;
import com.financialprocessingsystem.user.dto.response.UserResponse;
import com.financialprocessingsystem.user.exception.UserAlreadyExistException;
import com.financialprocessingsystem.user.exception.UserNotFoundException;
import com.financialprocessingsystem.user.mapper.UserMapper;
import com.financialprocessingsystem.user.model.User;
import com.financialprocessingsystem.user.repository.UserRepository;
import com.financialprocessingsystem.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    //todo password changing
    // will be added

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())){
            throw new UserAlreadyExistException("Email already exists");
        }
        var savedUser = userRepository.save(userMapper.toUser(userRequest));
        log.info("Created user: {}", savedUser);
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional
    public void updateUser(Long userId, UserUpdateRequest updateRequest) {
        User updateUser = findById(userId);
        updateUser.setFirstName(updateRequest.getFirstName());
        updateUser.setLastName(updateRequest.getLastName());
        userRepository.save(updateUser);
        log.info("Updated user: {}", updateUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userRepository.findAll(pageRequest)
                .getContent()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse findUserById(Long userId) {
        var user = findById(userId);
        log.info("Found user by: {}", user);
        return userMapper.toResponse(user);
    }

    private User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                ()-> new UserNotFoundException("User not found with given id: "+userId)
        );
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}
