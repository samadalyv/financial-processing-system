package com.financialprocessingsystem.user.service;

import com.financialprocessingsystem.user.dto.request.UserRequest;
import com.financialprocessingsystem.user.dto.request.UserUpdateRequest;
import com.financialprocessingsystem.user.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserResponse createUser(UserRequest userRequest);
    void updateUser(Long userId, UserUpdateRequest updateRequest);
    List<UserResponse> getAllUsers(int page, int size);
    UserResponse findUserById(Long userId);
//    void changePassword(Long userId, String oldPassword, String newPassword);

}
