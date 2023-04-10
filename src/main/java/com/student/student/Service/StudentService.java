package com.student.student.Service;

import com.student.student.Entity.CollegeEntity;
import com.student.student.Entity.StudentEntity;
import com.student.student.Model.StudentModel;
import com.student.student.Repository.StudentRepository;

import java.util.List;

public interface StudentService {
    StudentModel getStudentById(String id);
    String registerStudent(StudentModel studentModel);
    StudentModel loginStudent(String id, String password);
    String updateStudent(StudentModel studentModel);
    String deleteStudentById(String id);
    StudentRepository getStudentRepository();
    CollegeEntity getCollageByStudent(StudentEntity student, List<CollegeEntity> collages);

}
