package com.student.student.Controller;

import com.student.student.Model.*;
import com.student.student.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {
@Autowired
private SubjectService subjectService;
    @PostMapping("/createSubject")
    private String createSubject(@RequestBody final SubjectModel subjectModel) {
        return subjectService.createSubject(subjectModel);
    }
    @GetMapping("/{code}")
    private SubjectModel getSubjectByCode(@PathVariable(value = "code") final String code) {
        return subjectService.getSubjectByCode(code);
    }
    @GetMapping("/{code}/branch")
    private List<SubjectModel> getAllSubjectForBranch(@PathVariable(value = "code") final String branchCode){
        return subjectService.getAllSubjectsForBranch(branchCode);
    }
    @PutMapping("/updateFaculty")
    private String updateFacultyForSubject(@RequestBody final FacultyModel facultyModel, @RequestParam final String subjectCode) {
        return subjectService.updateFacultyForSubject(facultyModel,subjectCode);
    }
    @DeleteMapping("/remove/faculty")
    private String removeFacultyFromSubject(@RequestParam final String subjectCode,@RequestParam final String facultyCode){
        return subjectService.removeFacultyFromSubject(subjectCode,facultyCode);
    }
    @DeleteMapping("{code}")
    private String removeSubject(@PathVariable(value = "code") final String subjectCode){
        return subjectService.removeSubject(subjectCode);
    }
    @GetMapping("/getBranches")
    private BranchModel getBranchForSubject(@RequestParam final String subjectCode){
        return subjectService.getBranchForSubject(subjectCode);
    }
}
