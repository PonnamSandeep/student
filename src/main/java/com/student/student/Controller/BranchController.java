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
    @GetMapping("/{Code}")
    private BranchModel getBranchByCode(@PathVariable(value = "code") final String code){
        return branchService.getBranchByCode(code);
    }

    @GetMapping("/{code}")
    private List<BranchModel> getBranchForCollege(@PathVariable(value = "code") final String collageCode){
        return branchService.getAllBranchForCollage(collageCode);
    }
    @PutMapping("/updateSubject")
    private String updateSubject(@RequestBody final SubjectModel subjectModel,@RequestParam final  String branchCode){
        return branchService.updateSubjectForBranch(subjectModel,branchCode);
    }
    @DeleteMapping("/{subjectCode}")
    private String removeSubject(@PathVariable(value = "subjectCode") final String remove, final String delete){
        return branchService.removeSubjectFromBranch(remove, delete);
    }
    @DeleteMapping("/{branchCode}")
    private String removeBranch(@PathVariable(value = "branchCode") final String remove, final String delete){
        return branchService.removeBranchFromCollage(remove,delete);
    }
    @DeleteMapping("/{code}")
    private String removeBranch(@PathVariable(value = "code") final String remove){
        return branchService.removeBranch(remove);
    }
    @GetMapping("/getColleges")
    private List<CollegeModel> getColleges(@PathVariable(value = "getColleges") final String branchCode){
        return branchService.getCollagesForBranch(branchCode);
    }
    @GetMapping("/getStudents")
    private List<StudentModel> getStudents(@PathVariable(value = "getStudents") final String branchCode){
        return branchService.getStudentsForBranch(branchCode);
    }
}
