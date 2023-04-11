package com.student.student.Service.impl;

import com.student.student.Entity.BranchEntity;
import com.student.student.Entity.CollegeEntity;
import com.student.student.Entity.StudentEntity;
import com.student.student.Exception.InvalidDetailsException;
import com.student.student.Exception.InvalidStudentDetailsException;
import com.student.student.Exception.StudentNotFoundException;
import com.student.student.Model.BranchModel;
import com.student.student.Model.CollegeModel;
import com.student.student.Model.StudentModel;
import com.student.student.Repository.*;
import com.student.student.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
  @Autowired
    private StudentRepository studentRepository;
  @Autowired
  private CollegeRepository collageRepository;
  @Autowired
  private FacultyRepository facultyRepository;
  @Autowired
  private SubjectRepository subjectRepository;
  @Autowired
  private BranchRepository branchRepository;

  public StudentModel getStudentById(String id) {
      Optional<StudentEntity> studentEntity = getStudentRepository().findById(id);
      if (studentEntity.isPresent()) {
          StudentEntity student = studentEntity.get();
          StudentModel studentModel = new StudentModel();
          studentModel.setId(student.getId());
          studentModel.setName(student.getName());
          studentModel.setYear(student.getYear());
          studentModel.setPassword(student.getPassword());
          BranchEntity branchDetails = studentEntity.get().getBranchDetails();
          List<CollegeEntity> collageDetails = branchDetails.getCollageDetails();
          CollegeEntity collage = this.getCollageByStudent(student, collageDetails);
          BranchModel branchModel = new BranchModel();
          CollegeModel collageModel = new CollegeModel();
          branchModel.setBranchCode(branchDetails.getBranchCode());
          branchModel.setName(branchDetails.getName());
          collageModel.setCollageCode(collage.getCollageCode());
          collageModel.setName(collage.getName());
          studentModel.setBranchDetails(branchModel);
          studentModel.setCollegeCode(collage.getCollageCode());
          return studentModel;
      } else {
          System.out.println("student not found with this " + id);
          throw new StudentNotFoundException("Student not found please verify");
      }
  }
  public String registerStudent(StudentModel studentModel) {
      Optional<StudentEntity> student = studentRepository.findById(studentModel.getId());
      Optional<BranchEntity> branchEntity = branchRepository.findById(studentModel.getBranchDetails().getBranchCode());
      if(student.isEmpty()){
          if (branchEntity.isPresent()){
          if(null != studentModel.getId() && null != studentModel.getName() && null != studentModel.getPassword()) {
              StudentEntity studentEntity = new StudentEntity();
              studentEntity.setId(studentModel.getId());
              studentEntity.setName(studentModel.getName());
              studentEntity.setYear(studentModel.getYear());
              studentEntity.setPassword(studentModel.getPassword());
              studentEntity.setBranchDetails(branchEntity.get());
              getStudentRepository().save(studentEntity);
              return "Hi! Welcome "+ studentModel.getName();

          }
          else {
              throw new InvalidStudentDetailsException("Please provide mandatory fields");
          }
      }
      else {
      throw new InvalidDetailsException("please provide valid branch");
      }
      }
      else {
          return "the student already exists with this "+studentModel.getId();
      }
  }
  public StudentModel loginStudent(String id, String password){
      if(null != id && null != password) {
          Optional<StudentEntity> student = studentRepository.findById(id);
          if (student.isPresent()) {
              if (password.equals(student.get().getPassword())) {
                  System.out.print("login successfull");
                  StudentModel studentModel = new StudentModel();
                  studentModel.setId(student.get().getId());
                  studentModel.setName(student.get().getName());
                  studentModel.setYear(student.get().getYear());
                  studentModel.setPassword("******");
                  return studentModel;
              } else {
                  System.out.print("password incorrect");
                  return null;
              }
          } else {
              System.out.print("student doesn't exist");
              return null;
          }
      }
      else {
          System.out.print("invalid data");
          return null;
      }
  }
  public String updateStudent(StudentModel studentModel){
      StringBuilder response = new StringBuilder();
      Optional<StudentEntity> student = studentRepository.findById(studentModel.getId());
      if(student.isPresent()){
        if(null != studentModel.getName()){
            student.get().setName(studentModel.getName());
            response.append("name : "+student.get().getName()+", ");
        }
        if (null != studentModel.getYear()){
            student.get().setYear(studentModel.getYear());
            response.append("year : "+student.get().getYear()+", ");
        }
        if(null != studentModel.getPassword()){
            student.get().setPassword(studentModel.getPassword());
            response.append("password : "+student.get().getPassword()+", ");
        }
        if (!ObjectUtils.isEmpty(studentModel.getBranchDetails()) || !studentModel.getBranchDetails().equals(null)){
            Optional<BranchEntity> branchEntity = branchRepository.findById(studentModel.getBranchDetails().getBranchCode());
            if (branchEntity.isPresent()){
                student.get().setBranchDetails(branchEntity.get());
                response.append("branch : "+student.get().getBranchDetails().getName()+", ");

            }
            else {
                return "Please provide valid branch details";
            }
        }
        response.append("updated successfully");
        studentRepository.save(student.get());
        return response.toString();
      }
      else {
         return "student doesn't exist with this "+studentModel.getId();
      }
  }
  public String deleteStudentById(String id){
      if(null != id){
          Optional<StudentEntity> studentEntity = studentRepository.findById(id);
          studentRepository.delete(studentEntity.get());
          if (studentRepository.findById(id).isEmpty()){
          return "student deleted";
          }
          else {
              return "unable to delete";
          }
      }
      return "please provide id";
  }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    @Override
    public CollegeEntity getCollageByStudent(StudentEntity student, List<CollegeEntity> collages) {
      String collageCode = student.getCollegeCode();
      CollegeEntity collageEntity = new CollegeEntity();
      for (CollegeEntity collage : collages){
          if (collage.getCollageCode().equals(collageCode)){
              collageEntity = collage;
          }
      }
        return collageEntity;
    }

    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
}
