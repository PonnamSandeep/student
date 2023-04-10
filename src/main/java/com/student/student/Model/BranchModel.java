package com.student.student.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class BranchModel {
    private String branchCode;
    private String name;
    private List<CollegeModel> collageDetails;
    private Set<SubjectModel> subjectDetails;
    private List<StudentModel> studentsDetails;
}
