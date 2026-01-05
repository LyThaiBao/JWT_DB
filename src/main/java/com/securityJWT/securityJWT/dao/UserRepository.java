package com.securityJWT.securityJWT.dao;

import com.securityJWT.securityJWT.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
