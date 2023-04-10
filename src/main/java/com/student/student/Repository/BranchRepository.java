package com.student.student.Repository;

import com.student.student.Entity.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity,String> {
}
