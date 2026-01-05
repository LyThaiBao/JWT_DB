package com.securityJWT.securityJWT.dao;

import com.securityJWT.securityJWT.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom,Integer> {
}
