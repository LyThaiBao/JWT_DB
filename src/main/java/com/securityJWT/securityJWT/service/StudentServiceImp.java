package com.securityJWT.securityJWT.service;

import com.securityJWT.securityJWT.Exception.ResourceNotFoundException;
import com.securityJWT.securityJWT.dao.*;
import com.securityJWT.securityJWT.dto.RegisterFormDTO;
import com.securityJWT.securityJWT.dto.StudentDetailsDTO;
import com.securityJWT.securityJWT.dto.StudentListDTO;
import com.securityJWT.securityJWT.entity.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImp implements StudentService{
    private final StudentRepository studentRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final EnrollRepository enrollRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    @Override
    public List<StudentListDTO> getStudentLists() {
       List<Student> students =  this.studentRepository.findAllByUserEnableTrue();
       return students.stream().map(s->StudentListDTO.convertToDTO(s)).toList();
    }

    @Override
    public StudentDetailsDTO getStudentDetailById(Integer id) {
       Student student =  this.studentRepository.findById(id)
               .orElseThrow(()->new RuntimeException("Not found students"));
       if(!student.getUser().isEnable()){
           throw new ResourceNotFoundException("Not Found Student");
       }
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

    @Override
    public StudentDetailsDTO patchStudent(Integer id, Map<String, Object> patchPayload) {
        Student studentDb = this.studentRepository.findById(id).orElseThrow(()->new RuntimeException("Not Found Student"));
        User user = this.userRepository.findById(studentDb.getUser().getId()).orElseThrow(()->new RuntimeException("Not found user"));
        List<String> roles = this.userRoleRepository.findByUser(user).stream().map(r->r.getRole().getRoleName()).toList();
        List<String> classRooms = this.enrollRepository.findByStudent(studentDb).stream().map(s->s.getClassRoom().getName()).toList();
        StudentDetailsDTO studentDetailsDTO = StudentDetailsDTO.builder()
                .roles(roles)
                .classRooms(classRooms)
                .profile(StudentListDTO.convertToDTO(studentDb))
                .build();
        StudentDetailsDTO finalStudent= this.apply(studentDetailsDTO,patchPayload);

        //----update----
        System.out.println("Patch Final Student >>>"+finalStudent);
        studentDb.setEmail(finalStudent.getProfile().getEmail());
        studentDb.setName(finalStudent.getProfile().getName());
        studentDb.setPhone(finalStudent.getProfile().getPhone());
        this.studentRepository.save(studentDb);
//        System.out.println(">>>"+studentDetailsDTO);
        return finalStudent;
    }

    private StudentDetailsDTO apply(StudentDetailsDTO studentDetailsDTO, Map<String, Object> patchPayload){
        try{
//            //-------------------------------------------------------------------------
//            System.out.println("Student after update profile>>>"+patchPayload);
            StudentDetailsDTO update = objectMapper.readerForUpdating(studentDetailsDTO)
                    .readValue(objectMapper.writeValueAsString(patchPayload));
            return  update;
        }
        catch (JacksonException e){
            throw new RuntimeException("Lỗi khi merge dữ liệu: " + e.getMessage());
        }
    }

}
