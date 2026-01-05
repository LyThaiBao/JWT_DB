package com.securityJWT.securityJWT.dto;

import com.securityJWT.securityJWT.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentListDTO {
    private Integer id;
    private String name;
    private String email;
    private String phone;

    public static StudentListDTO convertToDTO(Student student){
        return StudentListDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .build();
    }

}
