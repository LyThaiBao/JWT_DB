package com.securityJWT.securityJWT.service;

import com.securityJWT.securityJWT.dao.EnrollRepository;
import com.securityJWT.securityJWT.dao.RoleRepository;
import com.securityJWT.securityJWT.dao.StudentRepository;
import com.securityJWT.securityJWT.dao.UserRoleRepository;
import com.securityJWT.securityJWT.dto.RegisterFormDTO;
import com.securityJWT.securityJWT.dto.StudentDetailsDTO;
import com.securityJWT.securityJWT.dto.StudentListDTO;
import com.securityJWT.securityJWT.entity.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImp implements StudentService{
    private final StudentRepository studentRepository;
    private final UserRoleRepository userRoleRepository;
    private final EnrollRepository enrollRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    @Override
    public List<StudentListDTO> getStudentLists() {
       List<Student> students =  this.studentRepository.findAll();
       return students.stream().map(s->StudentListDTO.convertToDTO(s)).toList();
    }

    @Override
    public StudentDetailsDTO getStudentDetailById(Integer id) {
       Student student =  this.studentRepository.findById(id)
               .orElseThrow(()->new RuntimeException("Not found students"));
      //-------------List role name-------------------
      User user = student.getUser();
      List<UserRole> roles =  this.userRoleRepository.findByUser(user);
      List<String> roleNames = roles.stream()
              .map(s->s.getRole()
              .getRoleName()).toList();

      //-------------List class room name------------------
      List<Enroll> enrolls = this.enrollRepository.findByStudent(student);
      List<String> classRoomNames =  enrolls.stream()
                .map(s->s.getClassRoom().getName()).toList();

        return StudentDetailsDTO.builder()
                .profile(StudentListDTO.convertToDTO(student))
                .roles(roleNames)
                .classRooms(classRoomNames)
                .build();

    }


    @Override
    @Transactional
    public StudentDetailsDTO registerStudent(RegisterFormDTO registerFormDTO) {
        List<String> roles = new ArrayList<>();
        List<String> classNames = new ArrayList<>();
        User userSave = this.userService.postUser(registerFormDTO);
        Student studentSaved = Student.builder()
                .name(registerFormDTO.getName())
                .email(registerFormDTO.getEmail())
                .phone(registerFormDTO.getPhone())
                .user(userSave)
                .build();
        this.studentRepository.save(studentSaved);
        Role role = this.roleRepository.findByRoleName("ROLE_STUDENT").orElseThrow(()->new RuntimeException("Not found Role"));
        UserRole userRole = UserRole.builder()
                .user(userSave)
                .role(role)
                .build();
        this.userRoleRepository.save(userRole);
        roles.add(role.getRoleName());
        return StudentDetailsDTO.builder()
                .profile(StudentListDTO.builder()
                        .id(studentSaved.getId())
                        .phone(studentSaved.getPhone())
                        .email(studentSaved.getEmail())
                        .name(studentSaved.getName())
                        .build())
                .classRooms(classNames)
                .roles(roles)
                .build();
    }

//    @Override
//    public StudentDetailsDTO patchStudent(Integer id, Map<String, StudentDetailsDTO> patchPayload) {
//        return null;
//    }
}
