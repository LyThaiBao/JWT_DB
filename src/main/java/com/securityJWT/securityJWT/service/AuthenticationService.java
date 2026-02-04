package com.securityJWT.securityJWT.service;

import com.securityJWT.securityJWT.dto.LoginRequest;
import com.securityJWT.securityJWT.dto.LoginResponse;

public interface AuthenticationService {
    LoginResponse authenticateUser(LoginRequest request);
//    LoginResponse refreshToken()
}
