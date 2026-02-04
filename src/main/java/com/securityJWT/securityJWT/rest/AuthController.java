package com.securityJWT.securityJWT.rest;

import com.securityJWT.securityJWT.dto.*;
import com.securityJWT.securityJWT.service.AuthenticationService;
import com.securityJWT.securityJWT.service.RefreshTokenService;
import com.securityJWT.securityJWT.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final StudentService studentService;
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    @PostMapping("/register")
    public StudentDetailsDTO registerUser(@RequestBody RegisterFormDTO registerData){
        return  this.studentService.registerStudent(registerData);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        System.out.println(">>>Login"+loginRequest);
        LoginResponse response = this.authenticationService.authenticateUser(loginRequest);
        // 2. Tạo Cookie chứa Access Token (HttpOnly)
        ResponseCookie accessCookie = ResponseCookie
                .from("accessToken",response.getAccessToken())
                .httpOnly(true)
                .secure(false)
                .path("/").sameSite("Strict")
                .maxAge(3600)
                .build();
        ResponseCookie refreshCookie = ResponseCookie
                .from("refreshToken",response.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .maxAge(7*24*3600)
                .path("/").sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE,refreshCookie.toString())
                .body("Login Success! Chìa khóa đã được cất vào két sắt.")
                ;
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refreshToken") String refresh){
      LoginResponse response =   this.refreshTokenService.refreshToken(refresh);
        ResponseCookie accessCookie = ResponseCookie
                .from("accessToken",response.getAccessToken())
                .httpOnly(true)
                .secure(false)
                .path("/").sameSite("Strict")
                .maxAge(3600)
                .build();
        ResponseCookie refreshCookie = ResponseCookie
                .from("refreshToken",response.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .maxAge(7*24*3600)
                .path("/").sameSite("Strict")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE,refreshCookie.toString())
                .body("Refresh Success");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "refreshToken") String refresh){
        System.out.println("Refresh >> "+refresh);

        this.refreshTokenService.logout(refresh);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
