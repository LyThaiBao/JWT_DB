package com.securityJWT.securityJWT.dto;


import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetailsDTO {

    // StudentListDTO contained ID
    private StudentListDTO profile;
    private List<String> roles;
    private List<String> classRooms;



}
