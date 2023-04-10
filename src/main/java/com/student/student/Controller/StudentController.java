package com.student.student.Controller;

import com.student.student.Model.StudentModel;
import com.student.student.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/Students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/{id}")
    public StudentModel getStudentById(@PathVariable(value = "id") final String id){
        StudentModel studentModel = studentService.getStudentById(id);
        return studentModel;
    }
    @PostMapping("/reg")
    public String registerStudent(@RequestBody final StudentModel studentModel){
        return studentService.registerStudent(studentModel);
    }
    @GetMapping("/login")
    public StudentModel loginStudent(@RequestParam final String id, @RequestParam final String password){
        return studentService.loginStudent(id, password);
    }
    @PutMapping("/update")
    public String updateStudent(@RequestBody final StudentModel update){
        return studentService.updateStudent(update);
    }
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable(value = "id") final String delete){
        return studentService.deleteStudentById(delete);
    }
}
