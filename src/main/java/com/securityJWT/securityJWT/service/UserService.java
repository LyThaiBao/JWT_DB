package com.securityJWT.securityJWT.service;

import com.securityJWT.securityJWT.dto.RegisterFormDTO;
import com.securityJWT.securityJWT.entity.User;

public interface UserService {
    User postUser(RegisterFormDTO register);
}
