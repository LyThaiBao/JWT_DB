package com.securityJWT.securityJWT.dao;

import com.securityJWT.securityJWT.entity.User;
import com.securityJWT.securityJWT.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
    public List<UserRole> findByUser(User user);
}
