package com.securityJWT.securityJWT.rest;

import com.securityJWT.securityJWT.dto.StudentDetailsDTO;
import com.securityJWT.securityJWT.dto.StudentListDTO;
import com.securityJWT.securityJWT.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    @GetMapping({"/",""})
    public List<StudentListDTO>getStudents(){
        System.out.println("Vo");
        return this.studentService.getStudentLists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDetailsDTO> getStudent(@PathVariable Integer id){
        return ResponseEntity.ok(this.studentService.getStudentDetailById(id));

    }
    @PatchMapping("/{id}")
    public StudentDetailsDTO updateStudent(@PathVariable Integer id, @RequestBody  Map<String,Object>patchPayload){
        System.out.println("PathVariable >>>"+patchPayload);
        return this.studentService.patchStudent(id,patchPayload);
    }

}
