package com.securityJWT.securityJWT.dao;

import com.securityJWT.securityJWT.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
   Optional<RefreshToken> findByToken(String token);
   @Transactional
   int deleteByUserId(Integer id);
}
