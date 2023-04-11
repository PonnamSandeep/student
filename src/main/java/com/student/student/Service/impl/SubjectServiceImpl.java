package com.student.student.Service.impl;

import com.student.student.Entity.*;
import com.student.student.Model.BranchModel;
import com.student.student.Model.FacultyModel;
import com.student.student.Model.StudentModel;
import com.student.student.Model.SubjectModel;
import com.student.student.Repository.BranchRepository;
import com.student.student.Repository.FacultyRepository;
import com.student.student.Repository.SubjectRepository;
import com.student.student.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Override
    public String createSubject(SubjectModel subjectModel) {
        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(subjectModel.getSubjectCode());
        if(subjectEntity.isEmpty()){
            if(null != subjectModel.getSubjectCode() && null != subjectModel.getName()){
                subjectEntity.get().setSubjectCode(subjectModel.getSubjectCode());
                subjectEntity.get().setName(subjectModel.getName());
                if (null != subjectModel.getFacultyDetails()){
                    List<FacultyModel> facultyModels = (List<FacultyModel>) subjectModel.getFacultyDetails();
                    List<FacultyEntity> facultyEntities = new ArrayList<>();
                    for (FacultyModel faculty : facultyModels){
                        FacultyEntity facultyEntity = new FacultyEntity();
                        facultyEntity.setId(faculty.getId());
                        facultyEntity.setName(faculty.getName());
                        facultyEntity.setPhoneNo(faculty.getPhoneNo());
                        facultyEntity.setSalary(faculty.getSalary());
                        facultyEntities.add(facultyEntity);
                    }
                    facultyRepository.saveAll(facultyEntities);
                }
                subjectRepository.save(subjectEntity.get());
                return "subject created";
            }
            else {
                return "please enter valid details";
            }
        }
        return "subject already existed";
    }

    @Override
    public SubjectModel getSubjectByCode(String code) {
        Optional<SubjectEntity> subject = subjectRepository.findById(code);
        if(subject.isPresent()){
            SubjectModel subjectModel = new SubjectModel();
            subjectModel.setSubjectCode(subject.get().getSubjectCode());
            subjectModel.setName(subject.get().getName());
            if(null != subject.get().getFacultyDetails()){
               List<FacultyEntity> facultyEntities = (List<FacultyEntity>) subject.get().getFacultyDetails();
               List<FacultyModel> facultyModels = new ArrayList<>();
               for(FacultyEntity faculty : facultyEntities){
                    FacultyModel facultyModel = new FacultyModel();
                    facultyModel.setId(faculty.getId());
                    facultyModel.setName(faculty.getName());
                    facultyModel.setPhoneNo(faculty.getPhoneNo());
                    facultyModel.setSalary(faculty.getSalary());
                    facultyModels.add(facultyModel);
                }
                subjectModel.setFacultyDetails((FacultyModel) facultyModels);
            }
            return subjectModel;
        }
        return null;
    }

    @Override
    public List<SubjectModel> getAllSubjectsForBranch(String branchCode) {
        Optional<BranchEntity> branch = branchRepository.findById(branchCode);
        if(branch.isPresent()){
            List<SubjectEntity> subjectEntities = (List<SubjectEntity>) branch.get().getSubjectDetails();
            if(null != branch.get().getSubjectDetails()){
                List<SubjectModel> subjectModels = new ArrayList<>();
                for (SubjectEntity subject : subjectEntities){
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
                return subjectModels;
                }
            }
            else {
                return null;
            }
            return null;
        }

    @Override
    public String updateFacultyForSubject(FacultyModel facultyModel, String subjectCode) {
        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(subjectCode);
        if (subjectEntity.isPresent()) {
            List<FacultyEntity> facultyEntities = (List<FacultyEntity>) subjectEntity.get().getFacultyDetails();
            List<SubjectEntity> subjectEntities = new ArrayList<>();
            FacultyEntity facultyEntity = new FacultyEntity();
            if (null != facultyModel.getId()) {
                facultyEntity.setId(facultyModel.getId());
            }
            if (null != facultyModel.getName()) {
                facultyEntity.setName(facultyModel.getName());
            }
            if (null != facultyModel.getPhoneNo()) {
                facultyEntity.setPhoneNo(facultyModel.getPhoneNo());
            }
            if(null != facultyModel.getSalary()){
                facultyEntity.setSalary(facultyModel.getSalary());
                facultyEntities.add(facultyEntity);
            }
            subjectEntity.get().setFacultyDetails((FacultyEntity) facultyEntities);
            subjectEntities.add(subjectEntity.get());
            subjectRepository.saveAll(subjectEntities);
        }
        return "there is no such subject";
    }

    @Override
    public String removeFacultyFromSubject(String facultyCode, String subjectCode) {
        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(subjectCode);
        if (subjectEntity.isPresent()) {
            List<FacultyEntity> facultyEntities = (List<FacultyEntity>) subjectEntity.get().getFacultyDetails();
            for (FacultyEntity faculty : facultyEntities) {
                if (facultyCode.equals(faculty.getId())) {
                    facultyRepository.delete(faculty);
                }
            }
        }
        return "there is no such faculty";
    }

    @Override
    public String removeSubject(String subjectCode) {
        if(null != subjectCode){
            Optional<SubjectEntity> subjectEntity = subjectRepository.findById(subjectCode);
            subjectRepository.delete(subjectEntity.get());
            if(subjectRepository.findById(subjectCode).isEmpty()){
                return "subject is deleted";
            }
            else {
                return "unable to delete subject";
            }
        }
        return "there is no such subject";
    }

    @Override
    public List<BranchModel> getBranchForSubject(String subjectCode) {
        Optional<SubjectEntity> subject = subjectRepository.findById(subjectCode);
        if(subject.isPresent()){
            List<BranchEntity> branchEntities = (List<BranchEntity>) subject.get().getBranchDetails();
            if(null != subject.get().getBranchDetails()){
                List<BranchModel> branchModels = new ArrayList<>();
                for(BranchEntity branch : branchEntities){
                    BranchModel branchModel = new BranchModel();
                    branchModel.setBranchCode(branch.getBranchCode());
                    branchModel.setName(branch.getName());
                    branchModels.add(branchModel);
                }
                return branchModels;
            }
            return null;
        }
        return null;
    }
}
