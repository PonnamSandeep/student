package com.student.student.Service.impl;

import com.student.student.Entity.*;
import com.student.student.Exception.*;
import com.student.student.Model.*;
import com.student.student.Repository.BranchRepository;
import com.student.student.Repository.CollegeRepository;
import com.student.student.Repository.FacultyRepository;
import com.student.student.Repository.SubjectRepository;
import com.student.student.Service.CollegeService;
import org.springframework.beans.BeanUtils;
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
    private SubjectRepository subjectRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Override
    public String createCollege(CollegeModel collegeModel) {
        Optional<CollegeEntity> college = collegeRepository.findById(collegeModel.getCollageCode());
        if (college.isEmpty()) {
            if (null != collegeModel.getCollageCode() && null != collegeModel.getName()) {
                CollegeEntity collegeEntity = new CollegeEntity();
                collegeEntity.setCollageCode(collegeModel.getCollageCode());
                collegeEntity.setName(collegeModel.getName());
                if (null != collegeModel.getBranchDetails()) {
                    List<BranchModel> branchModels = collegeModel.getBranchDetails();
                    List<BranchEntity> branchEntities = new ArrayList<>();
                    for (BranchModel branch : branchModels) {
                        BranchEntity branchEntity = new BranchEntity();
                        branchEntity.setBranchCode(branch.getBranchCode());
                        branchEntity.setName(branch.getName());
                        branchEntities.add(branchEntity);
                    }
                    collegeEntity.setBranchDetails(branchEntities);
                branchRepository.saveAll(branchEntities);
                }
                collegeRepository.save(collegeEntity);
                return "college created";
            } else {
                throw new InvalidDetailsException("please enter valid details");
            }
        }
        throw new AlreadyExistsException("college already existed");
    }

    @Override
    public List<CollegeModel> getCollegeById(String code) {
        Optional<CollegeEntity> collegeEntity = collegeRepository.findById(code);
        if(collegeEntity.isPresent()){
            List<CollegeModel> collegeModels = new ArrayList<>();
            CollegeModel collegeModel = new CollegeModel();
            collegeModel.setCollageCode(collegeEntity.get().getCollageCode());
            collegeModel.setName(collegeEntity.get().getName());
            if (null != collegeEntity.get().getBranchDetails()) {
                List<BranchModel> branchModels = new ArrayList<>();
                List<BranchEntity> branchEntities = collegeEntity.get().getBranchDetails();
                for (BranchEntity branch : branchEntities) {
                    BranchModel branchModel = new BranchModel();
                    branchModel.setBranchCode(branch.getBranchCode());
                    branchModel.setName(branch.getName());
                    branchModels.add(branchModel);
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
    public List<FacultyModel> getFacultyForCollege(String code) {
        Optional<CollegeEntity> collegeEntity = collegeRepository.findById(code);
        List<FacultyModel> facultyModelList = new ArrayList<>();
        if (collegeEntity.isPresent()) {
            List<BranchEntity> branchDetails = collegeEntity.get().getBranchDetails();
            for (BranchEntity branch : branchDetails){
                Set<SubjectEntity> subjectDetails = branch.getSubjectDetails();
                for (SubjectEntity subject : subjectDetails){
                    FacultyEntity facultyDetails = subject.getFacultyDetails();
                    FacultyModel facultyModel = new FacultyModel();
                    BeanUtils.copyProperties(facultyDetails,facultyModel);
                    facultyModelList.add(facultyModel);
                }
            }
            return facultyModelList;
        }
        return null;
    }

    @Override
    public String updateBranchForCollege(BranchModel branchModel, String code) {
        Optional<CollegeEntity> collegeEntity = collegeRepository.findById(code);
        if (collegeEntity.isPresent()) {
            List<BranchEntity> branchEntities = collegeEntity.get().getBranchDetails();
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
                    subjectEntity.setBranchDetails(branchEntity);
                    subjectEntities.add(subjectEntity);
                }
                branchEntity.setSubjectDetails(subjectEntities);
                subjectRepository.saveAll(subjectEntities);
                branchRepository.save(branchEntity);
                System.out.println("subject saved");
            }
            branchEntities.add(branchEntity);
            System.out.println("branch saved");
            collegeEntity.get().setBranchDetails(branchEntities);
            collegeRepository.save(collegeEntity.get());
            return "branch updated";
        }
        throw new DoesnotExitsException("there is no such branch");
    }

    @Override
    public String   removeBranchFromCollege(String collegeCode, String branchCode) {
        Optional<CollegeEntity> collegeEntity = collegeRepository.findById(collegeCode);
        if (collegeEntity.isPresent()){
            List<BranchEntity> branchDetails = collegeEntity.get().getBranchDetails();
            if(!branchCode.isEmpty()){
            Optional<BranchEntity> branchEntity = branchRepository.findById(branchCode);
            if (branchDetails.contains(branchEntity.get())){
            branchDetails.remove(branchEntity.get());
            collegeEntity.get().setBranchDetails(branchDetails);
            collegeRepository.save(collegeEntity.get());
                return "branch deleted from college";
            }
                throw new DoesnotExitsException("there is no such branch in college");
        }
        throw new InvalidDetailsException("branch shouldn't null");
    }
        throw new DoesnotExitsException("College not found");
}}
