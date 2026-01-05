package com.securityJWT.securityJWT.dao;

import com.securityJWT.securityJWT.entity.Enroll;
import com.securityJWT.securityJWT.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollRepository extends JpaRepository<Enroll,Integer> {
    public List<Enroll> findByStudent(Student student);
}
