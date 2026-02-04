package com.securityJWT.securityJWT.service;

import com.securityJWT.securityJWT.Exception.ResourceNotFoundException;
import com.securityJWT.securityJWT.dao.RefreshTokenRepository;
import com.securityJWT.securityJWT.dao.UserRepository;
import com.securityJWT.securityJWT.dto.LoginResponse;
import com.securityJWT.securityJWT.entity.RefreshToken;
import com.securityJWT.securityJWT.entity.User;
import com.securityJWT.securityJWT.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return this.refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("Not found user"));

       RefreshToken refreshToken = RefreshToken
               .builder()
               .user(user)
               .expired(Instant.now().plusMillis(7*24*3600))
               .token(UUID.randomUUID().toString())
               .build();
       return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        // So sánh: Nếu thời gian hết hạn < Thời gian hiện tại
        //compare  tra ve -1 , 1 , 0
        if(token.getExpired().compareTo(Instant.now())<0){
            this.refreshTokenRepository.delete(token);// het han thi xoa luon
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        RefreshToken refreshTokenDb = this.refreshTokenRepository.findByToken(refreshToken).orElseThrow(()->new RuntimeException("Not found token"));
        this.verifyExpiration(refreshTokenDb);
        User user = refreshTokenDb.getUser();
            return LoginResponse.builder()
                    .accessToken(this.jwtUtil.generateToken(user.getUsername()))
                    .refreshToken(refreshToken)
                    .build();
    }

    @Override
    public void logout( String refreshToken) {
        RefreshToken refreshTokenDb = this.refreshTokenRepository.findByToken(refreshToken).orElseThrow(()->new RuntimeException("Not found token"));
        this.verifyExpiration(refreshTokenDb);
        this.refreshTokenRepository.delete(refreshTokenDb);
    }
}
