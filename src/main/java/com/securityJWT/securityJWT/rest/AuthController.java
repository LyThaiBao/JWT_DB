package com.securityJWT.securityJWT.rest;

import com.securityJWT.securityJWT.dao.StudentRepository;
import com.securityJWT.securityJWT.dto.LoginRequest;
import com.securityJWT.securityJWT.dto.LoginResponse;
import com.securityJWT.securityJWT.dto.RegisterFormDTO;
import com.securityJWT.securityJWT.dto.StudentDetailsDTO;
import com.securityJWT.securityJWT.entity.User;
import com.securityJWT.securityJWT.security.JwtUtil;
import com.securityJWT.securityJWT.service.StudentService;
import com.securityJWT.securityJWT.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final StudentService studentService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @PostMapping("/register")
    public StudentDetailsDTO registerUser(@RequestBody RegisterFormDTO registerData){
        return  this.studentService.registerStudent(registerData);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{
            Authentication authentication = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
            String token = jwtUtil.generateToken(loginRequest.getUsername());
            return ResponseEntity.ok(LoginResponse.builder().token(token).build());
        } catch (Exception e) {
            return new ResponseEntity<>("Sai thong tin dang nhap", HttpStatus.UNAUTHORIZED);
        }

    }
}
