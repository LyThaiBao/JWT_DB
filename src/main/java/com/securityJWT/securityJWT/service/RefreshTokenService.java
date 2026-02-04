package com.securityJWT.securityJWT.service;

import com.securityJWT.securityJWT.dto.LoginResponse;
import com.securityJWT.securityJWT.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);
    RefreshToken createRefreshToken(Integer userId);
    RefreshToken verifyExpiration(RefreshToken token);
    LoginResponse refreshToken(String refreshToken);
    void logout(String refreshToken);
}
