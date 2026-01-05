package com.securityJWT.securityJWT.security;

import com.securityJWT.securityJWT.dao.RoleRepository;
import com.securityJWT.securityJWT.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class InitData implements CommandLineRunner {
    public final RoleRepository roleRepository;
    @Override
    public void run(String... args) throws Exception {
        if(!(this.roleRepository.findByRoleName("ROLE_STUDENT").isPresent())){
            Role roleStudent = new Role("ROLE_STUDENT");
            this.roleRepository.save(roleStudent);
        }
        if(!(this.roleRepository.findByRoleName("ROLE_TEACHER").isPresent())){
            Role roleTeacher = new Role("ROLE_TEACHER");
            this.roleRepository.save(roleTeacher);
        }
        if(!(this.roleRepository.findByRoleName("ROLE_ADMIN").isPresent())){
            Role roleAdmin = new Role("ROLE_ADMIN");
            this.roleRepository.save(roleAdmin);
        }

    }
}
