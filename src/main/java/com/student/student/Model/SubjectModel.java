package com.student.student.Model;

import com.student.student.Entity.BranchEntity;
import com.student.student.Entity.FacultyEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectModel {
    private String subjectCode;
    private String name;
    private BranchModel branchDetails;
    private FacultyModel facultyDetails;


}
