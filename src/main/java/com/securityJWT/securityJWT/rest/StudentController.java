package com.securityJWT.securityJWT.rest;

import com.securityJWT.securityJWT.dto.StudentListDTO;
import com.securityJWT.securityJWT.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    @GetMapping({"/",""})
    public List<StudentListDTO>getStudents(){
        return this.studentService.getStudentLists();
    }
}
