package com.student.student.Controller;

import com.student.student.Model.BranchModel;
import com.student.student.Model.CollegeModel;
import com.student.student.Model.StudentModel;
import com.student.student.Model.SubjectModel;
import com.student.student.Service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/branch")
public class BranchController {
    @Autowired
    private BranchService branchService;
    @PostMapping("/createBranch")
    private String createBranch(@RequestBody final BranchModel branchModel){
        return  branchService.createBranch(branchModel);
    }
    @GetMapping("/{code}")
    private BranchModel getBranchByCode(@PathVariable(value = "code") final String code){
        return branchService.getBranchByCode(code);
    }

    @GetMapping("/{code}/college")
    private List<BranchModel> getBranchForCollege(@PathVariable(value = "code") final String collageCode){
        return branchService.getAllBranchForCollage(collageCode);
    }
    @PutMapping("/updateSubject")
    private String updateSubject(@RequestBody final SubjectModel subjectModel,@RequestParam final  String branchCode){
        return branchService.updateSubjectForBranch(subjectModel,branchCode);
    }
    @DeleteMapping("/subject/remove")
    private String removeSubject(@RequestParam final String branchCode,@RequestParam final String subjectCode){
        return branchService.removeSubjectFromBranch(branchCode, subjectCode);
    }
    @DeleteMapping("/removeBranchFromCollege")
    private String removeBranch(@RequestParam final String collegeCode,@RequestParam final String branchCode){
        return branchService.removeBranchFromCollage(collegeCode,branchCode);
    }
    @DeleteMapping("/{code}")
    private String removeBranch(@PathVariable(value = "code") final String remove){
        return branchService.removeBranch(remove);
    }
    @GetMapping("/getColleges")
    private List<CollegeModel> getColleges(@RequestParam final String branchCode){
        return branchService.getCollagesForBranch(branchCode);
    }
    @GetMapping("/getStudents")
    private List<StudentModel> getStudents(@RequestParam final String branchCode){
        return branchService.getStudentsForBranch(branchCode);
    }
}
