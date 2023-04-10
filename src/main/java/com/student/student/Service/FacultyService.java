package com.student.student.Service;

import com.student.student.Model.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FacultyService {
        String createFaculty(FacultyModel facultyModel);

        FacultyModel getFacultyById(String code);

        List<FacultyModel> getFacultyForBranch(String branchCode);

        String updateSubjectForFaculty(SubjectModel subjectModel, String Id);

        String removeSubjectForFaculty(String facultyId, String subjectCode);

        String removeFaculty(String Id);

        ResponseEntity<Object> getSubjectsForFaculty(String Id);

        List<StudentModel> getStudentsForFaculty(String code);
}
