package com.student.student.Service.impl;

import com.student.student.Entity.*;
import com.student.student.Model.*;
import com.student.student.Repository.BranchRepository;
import com.student.student.Repository.CollegeRepository;
import com.student.student.Repository.FacultyRepository;
import com.student.student.Service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CollegeServiceImpl implements CollegeService {
    @Autowired
    private CollegeRepository collegeRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Override
    public String createCollege(CollegeModel collegeModel) {
        Optional<CollegeEntity> collegeEntity = collegeRepository.findById(collegeModel.getCollageCode());
        if (collegeEntity.isEmpty()) {
            if (null != collegeModel.getCollageCode() && null != collegeModel.getName()) {
                collegeEntity.get().setCollageCode(collegeModel.getCollageCode());
                collegeEntity.get().setName(collegeModel.getName());
                if (null != collegeModel.getBranchDetails()) {
                    List<BranchModel> branchModels = collegeModel.getBranchDetails();
                    List<BranchEntity> branchEntities = new ArrayList<>();
                    for (BranchModel branch : branchModels) {
                        BranchEntity branchEntity = new BranchEntity();
                        branchEntity.setBranchCode(branch.getBranchCode());
                        branchEntity.setName(branch.getName());
                    }
                    collegeEntity.get().setBranchDetails(branchEntities);
                branchRepository.saveAll(branchEntities);
                }
                collegeRepository.save(collegeEntity.get());
                return "college created";
            } else {
                return "please enter valid details";
            }
        }
        return "college already existed";
    }

    @Override
    public List<CollegeModel> getCollegeById(String code) {
        Optional<CollegeEntity> collegeEntity = collegeRepository.findById(code);
        if(collegeEntity.isPresent()){
            List<CollegeModel> collegeModels = new ArrayList<>();
            CollegeModel collegeModel = new CollegeModel();
            collegeModel.setCollageCode(collegeEntity.get().getCollageCode());
            collegeModel.setName(collegeEntity.get().getName());
            if (null != collegeModel.getBranchDetails()) {
                List<BranchModel> branchModels = collegeModel.getBranchDetails();
                List<BranchEntity> branchEntities = new ArrayList<>();
                for (BranchModel branch : branchModels) {
                    BranchEntity branchEntity = new BranchEntity();
                    branchEntity.setBranchCode(branch.getBranchCode());
                    branchEntity.setName(branch.getName());
                }
                collegeModel.setBranchDetails(branchModels);
                collegeModels.add(collegeModel);
            }
            return collegeModels;
        }
        return null;
    }

    @Override
    public List<BranchModel> getBranchForCollege(String collegeCode) {
        Optional<CollegeEntity> collegeEntity = collegeRepository.findById(collegeCode);
        if(collegeEntity.isPresent()){
            List<BranchEntity> branchEntities = collegeEntity.get().getBranchDetails();
            if(null != collegeEntity.get().getBranchDetails()){
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
                        FacultyModel facultyModel = new FacultyModel();
                        facultyModel.setId(facultyEntity.getId());
                        facultyModel.setName(facultyEntity.getName());
                        facultyModel.setSalary(facultyEntity.getSalary());
                        facultyModel.setPhoneNo(facultyEntity.getPhoneNo());
                        subjectModel.setFacultyDetails(facultyModel);
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
    public FacultyModel getFacultyById(String Id) {
        Optional<FacultyEntity> faculty = facultyRepository.findById(Id);
        if (faculty.isPresent()) {
            FacultyModel facultyModel = new FacultyModel();
            facultyModel.setId(faculty.get().getId());
            facultyModel.setName(faculty.get().getName());
            facultyModel.setPhoneNo(faculty.get().getPhoneNo());
            facultyModel.setSalary(faculty.get().getSalary());
            if (null != faculty.get().getSubjects()) {
                Set<SubjectEntity> subjectDetails = faculty.get().getSubjects();
                Set<SubjectModel> subjectModels = new HashSet<>();
                for (SubjectEntity subject : subjectDetails) {
                    SubjectModel subjectModel = new SubjectModel();
                    subjectModel.setSubjectCode(subject.getSubjectCode());
                    subjectModel.setName(subject.getName());
                    subjectModels.add(subjectModel);
                }
                facultyModel.setSubjects(subjectModels);
            }
            return facultyModel;
        }
        return null;
    }

    @Override
    public String updateBranchForCollege(BranchModel branchModel, String code) {
        Optional<CollegeEntity> collegeEntity = collegeRepository.findById(code);
        if (collegeEntity.isPresent()) {
            List<BranchEntity> branchEntities = collegeEntity.get().getBranchDetails();
            List<CollegeEntity> collegeEntities = new ArrayList<>();
            BranchEntity branchEntity = new BranchEntity();
            if (null != branchModel.getBranchCode()) {
                branchEntity.setBranchCode(branchModel.getBranchCode());
            }
            if (null != branchModel.getName()) {
                branchEntity.setName(branchModel.getName());
            }
            if (null != branchModel.getSubjectDetails()) {
                Set<SubjectModel> subjectModel = branchModel.getSubjectDetails();
                Set<SubjectEntity> subjectEntities = new HashSet<>();
                for (SubjectModel subject : subjectModel) {
                    SubjectEntity subjectEntity = new SubjectEntity();
                    subjectEntity.setSubjectCode(subject.getSubjectCode());
                    subjectEntity.setName(subject.getName());
                    subjectEntities.add(subjectEntity);
                }
            }
            collegeEntity.get().setBranchDetails(branchEntities);
            collegeEntities.add(collegeEntity.get());
            collegeRepository.saveAll(collegeEntities);
        }
        return "there is no such branch";
    }

    @Override
    public String removeBranchFromCollege(String collegeCode, String branchCode) {
        Optional<CollegeEntity> collegeEntity = collegeRepository.findById(collegeCode);
        if (collegeEntity.isPresent()){
            List<BranchEntity> branchDetails = collegeEntity.get().getBranchDetails();
            if(null != branchCode){
            Optional<BranchEntity> branchEntity = branchRepository.findById(branchCode);
            if (branchDetails.contains(branchEntity.get())){
            branchDetails.remove(branchEntity.get());
            collegeEntity.get().setBranchDetails(branchDetails);
            collegeRepository.save(collegeEntity.get());
                return "branch deleted from college";
            }
                return "there is no such branch in college";
        }
        return "branch shouldn't null";
    }
        return "College not found";
}}
