package com.financialprocessingsystem.auth.service;

import com.financialprocessingsystem.auth.request.AuthenticationRequest;
import com.financialprocessingsystem.auth.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);
}
