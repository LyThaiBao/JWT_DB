package com.securityJWT.securityJWT.service;

import com.securityJWT.securityJWT.dao.RefreshTokenRepository;
import com.securityJWT.securityJWT.dao.UserRepository;
import com.securityJWT.securityJWT.dto.LoginRequest;
import com.securityJWT.securityJWT.dto.LoginResponse;
import com.securityJWT.securityJWT.entity.RefreshToken;
import com.securityJWT.securityJWT.entity.User;
import com.securityJWT.securityJWT.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    public LoginResponse authenticateUser(LoginRequest request) {
        System.out.println("Request Login>>> "+request);
       try{
           Authentication authentication =  this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
           User user = this.userRepository.findByUsername(request.getUsername())
                   .orElseThrow(()->new RuntimeException("Not found Student"));
           this.refreshTokenRepository.deleteByUserId(user.getId());

            String accessToken = this.jwtUtil.generateToken(request.getUsername());
            RefreshToken refreshToken = this.refreshTokenService.createRefreshToken(user.getId());
            LoginResponse response =  LoginResponse
                    .builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getToken())
                    .build();
            this.refreshTokenRepository.save(refreshToken);
            return response;
       }
       catch (Exception e){
           e.printStackTrace();
           throw new RuntimeException("Fail to Login");
       }
    }
}
