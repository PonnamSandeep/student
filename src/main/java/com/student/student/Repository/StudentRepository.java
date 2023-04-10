package com.student.student.Repository;

import com.student.student.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, String> {
}
