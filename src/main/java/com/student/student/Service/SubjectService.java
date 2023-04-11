package com.student.student.Service;

import com.student.student.Model.BranchModel;
import com.student.student.Model.FacultyModel;
import com.student.student.Model.SubjectModel;

import java.util.List;

public interface SubjectService {
    String createSubject(SubjectModel subjectModel);
    SubjectModel getSubjectByCode(String code);
    List<SubjectModel> getAllSubjectsForBranch(String branchCode);
    String updateFacultyForSubject(FacultyModel facultyModel, String subjectCode);
    String removeFacultyFromSubject(String facultyCode, String subjectCode);
    String removeSubject(String subjectCode);
    List<BranchModel> getBranchForSubject(String subjectCode);
}
