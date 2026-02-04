package com.securityJWT.securityJWT.service;

import com.securityJWT.securityJWT.dao.UserRepository;
import com.securityJWT.securityJWT.dto.RegisterFormDTO;
import com.securityJWT.securityJWT.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public User postUser(RegisterFormDTO register) {
        System.out.println(">>>"+register);
       //Create user
        User user = User.builder()
                    .enable(true)
                    .username(register.getUsername())
                    .password(this.passwordEncoder.encode(register.getPassword()))
                    .build();
        this.userRepository.save(user);
        return user;
    }
    public User deleteUser(Integer id){
        User user = this.userRepository.findById(id).orElseThrow(()->new RuntimeException("Not found Student"));
        this.userRepository.delete(user);
        return user;

    }
}
