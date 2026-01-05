package com.securityJWT.securityJWT.service;

import com.securityJWT.securityJWT.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleService extends JpaRepository<Role,Integer> {
}
