package com.student.student.Service;

import com.student.student.Model.BranchModel;
import com.student.student.Model.CollegeModel;
import com.student.student.Model.FacultyModel;

import java.util.List;

public interface CollegeService {
    String createCollege(CollegeModel collageModel);
    List<CollegeModel> getCollegeById(String code);
    List<BranchModel> getBranchForCollege(String branchCode);
    List<FacultyModel> getFacultyForCollege(String Id);
    String updateBranchForCollege(BranchModel branchModel, String code);
    String removeBranchFromCollege(String collegeCode, String branchCode);
}
