package com.securityJWT.securityJWT.service;

import com.securityJWT.securityJWT.dao.UserRepository;
import com.securityJWT.securityJWT.dto.RegisterFormDTO;
import com.securityJWT.securityJWT.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;

    @Override
    public User postUser(RegisterFormDTO register) {
        System.out.println(">>>"+register);
       //Create user
        User user = User.builder()
                    .enable(true)
                    .username(register.getUsername())
                    .password(register.getPassword())
                    .build();
        this.userRepository.save(user);
        System.out.println("User >>"+user);
        return user;
    }
}
