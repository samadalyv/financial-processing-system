package com.financialprocessingsystem.user.service;

import com.financialprocessingsystem.user.dto.request.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void createUser(UserRequest userRequest);

}
