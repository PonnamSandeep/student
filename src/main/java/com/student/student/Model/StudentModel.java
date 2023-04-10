package com.student.student.Model;

import com.student.student.Entity.BranchEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentModel {
    private String id;
    private String name;
    private String year;
    private String password;
    private String collegeCode;
    private BranchModel branchDetails;
}
