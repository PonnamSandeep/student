package com.student.student.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CollegeModel {
    private String collageCode;
    private String name;
    private List<BranchModel> branchDetails;
}
