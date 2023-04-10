package com.student.student.Repository;

import com.student.student.Entity.FacultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<FacultyEntity,String> {
}
