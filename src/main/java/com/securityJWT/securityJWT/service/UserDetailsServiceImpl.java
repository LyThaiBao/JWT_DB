package com.securityJWT.securityJWT.service;

import com.securityJWT.securityJWT.dao.UserRepository;
import com.securityJWT.securityJWT.dao.UserRoleRepository;
import com.securityJWT.securityJWT.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("Not found user"));
        List<SimpleGrantedAuthority> userRoles = this.userRoleRepository.findByUser(user).stream().map(s->new SimpleGrantedAuthority(s.getRole().getRoleName())).toList();
       return org.springframework.security.core.userdetails.User
               .builder()
               .username(user
               .getUsername())
               .password(user.getPassword())
               .disabled(!user.isEnable())
               .authorities(userRoles).build();
    }
}
