package com.student.student.Service;

import com.student.student.Model.BranchModel;
import com.student.student.Model.CollegeModel;
import com.student.student.Model.FacultyModel;
import com.student.student.Model.StudentModel;

import java.util.List;

public interface CollegeService {
    String createCollege(CollegeModel collageModel);
    List<CollegeModel> getCollegeById(String code);
    List<BranchModel> getBranchForCollege(String branchCode);
    FacultyModel getFacultyById(String Id);
    String updateBranchForCollege(BranchModel branchModel, String code);
    String removeBranchFromCollege(String collegeCode, String branchCode);
}
