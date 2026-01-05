package com.securityJWT.securityJWT.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterFormDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;


}
