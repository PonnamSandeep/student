package com.student.student.Controller;

import com.student.student.Model.BranchModel;
import com.student.student.Model.CollegeModel;
import com.student.student.Model.FacultyModel;
import com.student.student.Service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/college")
public class CollegeController {
    @Autowired
    private CollegeService collegeService;

    @PostMapping("/createCollege")
    private String createCollege(@RequestBody final CollegeModel collageModel) {
        return collegeService.createCollege(collageModel);
    }

    @GetMapping("/{code}")
    private List<CollegeModel> getCollegeById(@PathVariable(value = "code") final String code) {
        return collegeService.getCollegeById(code);
    }
    @GetMapping("/{code}/branch")
    private List<BranchModel> getBranchForCollege(@PathVariable(value = "code") final String collageCode) {
        return collegeService.getBranchForCollege(collageCode);
    }
    @GetMapping("/{id}/faculty")
    private FacultyModel getFacultyById(@PathVariable(value = "id") final String id) {
        return collegeService.getFacultyById(id);
    }

    @PutMapping("/updateSubject")
    private String updateBranchForCollege(@RequestBody final BranchModel branchModel, @RequestParam final String code) {
        return collegeService.updateBranchForCollege(branchModel,code);
    }
    @DeleteMapping("/{branchCode}")
    private String removeBranch(@PathVariable(value = "branchCode") final String remove, final String delete){
        return collegeService.removeBranchFromCollege(remove,delete);
    }
}
