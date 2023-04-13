package com.student.student.Controller;

import com.student.student.Model.*;
import com.student.student.Service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/faculty")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;

    @PostMapping("/createFaculty")
    private String createFaculty(@RequestBody final FacultyModel facultyModel) {
        return facultyService.createFaculty(facultyModel);
    }

    @GetMapping("/{id}")
    private FacultyModel getFacultyById(@PathVariable(value = "id") final String id) {
        return facultyService.getFacultyById(id);
    }

    @GetMapping("/{code}/branches")
    private List<FacultyModel> getFacultyForBranch(@PathVariable(value = "code") final String branchCode) {
        return facultyService.getFacultyForBranch(branchCode);
    }

    @PutMapping("/updateSubject")
        private String updateSubject(@RequestBody final SubjectModel subjectModel, @RequestParam final String id) {
        return facultyService.updateSubjectForFaculty(subjectModel,id);
    }

    @DeleteMapping("/jbjbjb")
    private String removeSubject(@PathVariable(value = "subjectCode") final String facultyId, final String subjectCode) {
        return facultyService.removeSubjectForFaculty(facultyId,subjectCode);
    }
    @DeleteMapping("/{id}")
    private String removeFaculty(@PathVariable final String id) {
        return facultyService.removeFaculty(id);
    }

    @GetMapping("/{id}/subjects")
    private ResponseEntity<Object> getSubjects(@PathVariable(value = "id") final String id) {
        return facultyService.getSubjectsForFaculty(id);
    }
}
