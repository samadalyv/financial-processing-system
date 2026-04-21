package com.financialprocessingsystem.user.mapper;

import com.financialprocessingsystem.user.dto.request.UserRequest;
import com.financialprocessingsystem.user.dto.response.UserResponse;
import com.financialprocessingsystem.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password",
            expression = "java(passwordEncoder.encode(userRequest.getPassword()))")
    User toEntity(UserRequest userRequest, PasswordEncoder passwordEncoder);

    UserResponse toResponse(User user);

}
