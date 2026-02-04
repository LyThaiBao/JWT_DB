package com.securityJWT.securityJWT.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {

        //set ma loi
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        JwtErrorResponse errorResponse = JwtErrorResponse
                .builder()
                .status(401)
                .message("Invalid Token or Expired ")
                .build();
        String json = new ObjectMapper().writeValueAsString(errorResponse);
        // ghi ra Response (tra ve client)
        response.getWriter().write(json);
    }
}
