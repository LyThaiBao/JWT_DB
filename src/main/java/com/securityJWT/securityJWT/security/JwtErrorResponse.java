package com.securityJWT.securityJWT.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtErrorResponse {
    private Integer status;
    private String message;
}
