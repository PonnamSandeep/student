package com.student.student.Service;

import com.student.student.Model.BranchModel;
import com.student.student.Model.CollegeModel;
import com.student.student.Model.StudentModel;
import com.student.student.Model.SubjectModel;

import java.util.List;

public interface BranchService {
    String createBranch(BranchModel branchModel);
    BranchModel getBranchByCode(String code);
    List<BranchModel> getAllBranchForCollage(String collageCode);
    String updateSubjectForBranch(SubjectModel subjectModel,String branchCode);
    String removeSubjectFromBranch(String branchCode,String subjectCode);
    String removeBranchFromCollage(String collageCode,String branchCode);
    String removeBranch(String branchCode);
    List<CollegeModel> getCollagesForBranch(String branchCode);
    List<StudentModel> getStudentsForBranch(String branchCode);
}
