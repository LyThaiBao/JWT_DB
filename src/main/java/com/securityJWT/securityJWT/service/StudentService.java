package com.securityJWT.securityJWT.service;


import com.securityJWT.securityJWT.dto.RegisterFormDTO;
import com.securityJWT.securityJWT.dto.StudentDetailsDTO;
import com.securityJWT.securityJWT.dto.StudentListDTO;
import com.securityJWT.securityJWT.entity.Student;
import com.securityJWT.securityJWT.entity.User;

import java.util.List;
import java.util.Map;

public interface StudentService {
    List<StudentListDTO> getStudentLists();
    StudentDetailsDTO getStudentDetailById(Integer id);
    StudentDetailsDTO registerStudent(RegisterFormDTO registerFormDTO);
    StudentDetailsDTO patchStudent(Integer id, Map<String,Object>patchPayload);

}
