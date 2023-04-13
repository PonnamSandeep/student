package com.student.student.Service.impl;

import com.student.student.Entity.*;
import com.student.student.Exception.*;
import com.student.student.Model.*;
import com.student.student.Repository.BranchRepository;
import com.student.student.Repository.CollegeRepository;
import com.student.student.Repository.FacultyRepository;
import com.student.student.Repository.SubjectRepository;
import com.student.student.Service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BranchServiceImpl implements BranchService {
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private CollegeRepository collageRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Override
    public String createBranch(BranchModel branchModel) {
        Optional<BranchEntity> branch = branchRepository.findById(branchModel.getBranchCode());
        if(branch.isEmpty()){
            if(null != branchModel.getBranchCode() && null != branchModel.getName()){
                BranchEntity branchEntity = new BranchEntity();
              branchEntity.setBranchCode(branchModel.getBranchCode());
              branchEntity.setName(branchModel.getName());
              if (null != branchModel.getSubjectDetails()){
                  Set<SubjectModel> subjectDetails = branchModel.getSubjectDetails();
                  Set<SubjectEntity> subjectEntities = new HashSet<>();
                  for (SubjectModel subject : subjectDetails){
                      SubjectEntity subjectEntity = new SubjectEntity();
                      subjectEntity.setSubjectCode(subject.getSubjectCode());
                      subjectEntity.setName(subject.getName());
                      subjectEntity.setBranchDetails(branchEntity);
                      subjectEntities.add(subjectEntity);
                      subjectRepository.save(subjectEntity);
                  }
                  branchEntity.setSubjectDetails(subjectEntities);
              }
              branchRepository.save(branchEntity);
              return "branch created";
            }
            else {
                throw new InvalidDetailsException("please enter valid details");
            }
        }
        throw new AlreadyExistsException("branch already existed");
    }

    @Override
    public BranchModel getBranchByCode(String code) {
        Optional<BranchEntity> branch = branchRepository.findById(code);
        if(branch.isPresent()){
            BranchModel branchModel = new BranchModel();
            branchModel.setBranchCode(branch.get().getBranchCode());
            branchModel.setName(branch.get().getName());
            if(null != branch.get().getStudentsDetails()){
                Set<SubjectEntity> subjectDetails = branch.get().getSubjectDetails();
                Set<SubjectModel> subjectModels = new HashSet<>();
                for(SubjectEntity subject : subjectDetails){
                    SubjectModel subjectModel = new SubjectModel();
                    subjectModel.setSubjectCode(subject.getSubjectCode());
                    subjectModel.setName(subject.getName());
                    subjectModels.add(subjectModel);
                }
                branchModel.setSubjectDetails(subjectModels);
            }
            return branchModel;
        }
        throw new FailedException("branch doesn't exists");
    }

    @Override
    public List<BranchModel> getAllBranchForCollage(String collageCode) {
        Optional<CollegeEntity> college = collageRepository.findById(collageCode);
        if(college.isPresent()){
            if(null != college.get().getBranchDetails()){
                List<BranchEntity> branchEntities = college.get().getBranchDetails();
                List<BranchModel> branchModels = new ArrayList<>();
                for (BranchEntity branch : branchEntities){
                    BranchModel branchModel = new BranchModel();
                    branchModel.setBranchCode(branch.getBranchCode());
                    branchModel.setName(branch.getName());
                   Set<SubjectEntity> subjectEntities = branch.getSubjectDetails();
                   Set<SubjectModel> subjectModels = new HashSet<>();
                   for(SubjectEntity subject : subjectEntities){
                       SubjectModel subjectModel = new SubjectModel();
                       subjectModel.setSubjectCode(subject.getSubjectCode());
                       subjectModel.setName(subject.getName());
                       FacultyEntity facultyEntity = subject.getFacultyDetails();
                       if(null != facultyEntity) {
                           FacultyModel facultyModel = new FacultyModel();
                           facultyModel.setId(facultyEntity.getId());
                           facultyModel.setName(facultyEntity.getName());
                           facultyModel.setSalary(facultyEntity.getSalary());
                           facultyModel.setPhoneNo(facultyEntity.getPhoneNo());
                           subjectModel.setFacultyDetails(facultyModel);
                       }
                       subjectModels.add(subjectModel);
                   }
                   branchModel.setSubjectDetails(subjectModels);
                   branchModels.add(branchModel);
                }
                return branchModels;
            }
            else {
                return null;
            }
        }
        else {
        return null;
        }
    }

    @Override
    public String updateSubjectForBranch(SubjectModel subjectModel, String branchCode) {
        Optional<BranchEntity> branchEntity = branchRepository.findById(branchCode);
        if (branchEntity.isPresent()) {
            Set<SubjectEntity> subjectEntities = branchEntity.get().getSubjectDetails();
            Set<BranchEntity> branchEntities = new HashSet<>();
            SubjectEntity subjectEntity = new SubjectEntity();
            subjectEntity.setBranchDetails(branchEntity.get());
            if (null != subjectModel.getSubjectCode()) {
                subjectEntity.setSubjectCode(subjectModel.getSubjectCode());
            }
            if (null != subjectModel.getName()) {
                subjectEntity.setName(subjectModel.getName());
            }
            if (null != subjectModel.getFacultyDetails()) {
                FacultyModel facultyModel = subjectModel.getFacultyDetails();
                FacultyEntity facultyEntity = new FacultyEntity();
                facultyEntity.setId(facultyModel.getId());
                facultyEntity.setName(facultyModel.getName());
                facultyEntity.setSalary(facultyModel.getSalary());
                facultyEntity.setPhoneNo(facultyModel.getPhoneNo());
                facultyEntity.setSubjects(Set.of(subjectEntity));
                subjectEntity.setFacultyDetails(facultyEntity);
                subjectRepository.save(subjectEntity);
                subjectEntities.add(subjectEntity);
            }
            branchEntity.get().setSubjectDetails(subjectEntities);
            branchEntities.add(branchEntity.get());
            branchRepository.saveAll(branchEntities);
            return "updated";
        }
        throw new FailedException("there is no such branch");
    }

    @Override
    public String removeSubjectFromBranch(String branchCode, String subjectCode) {
        Optional<BranchEntity> branchEntity = branchRepository.findById(branchCode);
        if (branchEntity.isPresent()){
            Set<SubjectEntity> subjectDetails = branchEntity.get().getSubjectDetails();
            Set<SubjectEntity> subjectDetailsnew  = new HashSet<>();
            for (SubjectEntity subject : subjectDetails){
                if (subjectCode.equals(subject.getSubjectCode())){
                    subject.setBranchDetails(null);
                }
                else {
                   subjectDetailsnew.add(subject);
                }
            }
            branchEntity.get().setSubjectDetails(subjectDetailsnew);
            branchRepository.saveAndFlush(branchEntity.get());
            return "branch Removed";
        }
        throw new FailedException("Branch doesn't exist");
    }

    @Override
    public String removeBranchFromCollage(String collageCode, String branchCode) {
        Optional<CollegeEntity> collegeEntity = collageRepository.findById(collageCode);
        if (collegeEntity.isPresent()){
            List<BranchEntity> branchDetails = collegeEntity.get().getBranchDetails();
            List<BranchEntity> branchEntities = new ArrayList<>();
            if(null != branchCode){
                for (BranchEntity branch : branchDetails){
                    if(!branchCode.equals(branch.getBranchCode())){

                        branchEntities.add(branch);                    }
                }
                collegeEntity.get().setBranchDetails(branchEntities);
                collageRepository.save(collegeEntity.get());
                return "branch removed";
            }
            throw new InvalidDetailsException("branchCode shouldn't null");
        }
        throw new DoesnotExitsException("there is no such branch in college");
    }

    @Override
    public String removeBranch(String branchCode) {
        if(null != branchCode){
            Optional<BranchEntity> branchEntity = branchRepository.findById(branchCode);
            if (branchEntity.isPresent()){
            branchRepository.deleteById(branchEntity.get().getBranchCode());
            if(branchRepository.findById(branchCode).isEmpty()){
                return "branch deleted from college";
            }
            else {
                throw new FailedException("unable to delete branch");
            }
        }}
        throw new DoesnotExitsException("there is no such branch in college");
    }

    @Override
    public List<CollegeModel> getCollagesForBranch(String branchCode) {
        Optional<BranchEntity> branch = branchRepository.findById(branchCode);
        if(branch.isPresent()){
            if(null != branch.get().getCollageDetails()){
                List<CollegeEntity> collageDetails = branch.get().getCollageDetails();
                List<CollegeModel> collageModels = new ArrayList<>();
                for (CollegeEntity collage : collageDetails){
                    CollegeModel collageModel = new CollegeModel();
                    collageModel.setCollageCode(collage.getCollageCode());
                    collageModel.setName(collage.getName());
                    collageModels.add(collageModel);
                    }
                return collageModels;
                }
                return null;
            }
        return null;
    }

    @Override
    public List<StudentModel> getStudentsForBranch(String branchCode) {
        Optional<BranchEntity> branch = branchRepository.findById(branchCode);
        if(branch.isPresent()){
            if(null != branch.get().getStudentsDetails()){
                List<StudentEntity> studentEntities = branch.get().getStudentsDetails();
                List<StudentModel> studentModels = new ArrayList<>();
                for(StudentEntity student : studentEntities){
                    StudentModel studentModel = new StudentModel();
                    studentModel.setId(student.getId());
                    studentModel.setName(student.getName());
                    studentModel.setYear(student.getYear());
                    studentModel.setPassword(student.getPassword());
                    studentModels.add(studentModel);
                }
                return studentModels;
            }
            return null;
        }
        return null;
    }
}
