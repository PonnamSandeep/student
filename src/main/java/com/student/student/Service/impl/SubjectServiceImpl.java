package com.student.student.Service.impl;

import com.student.student.Entity.*;
import com.student.student.Exception.*;
import com.student.student.Model.BranchModel;
import com.student.student.Model.FacultyModel;
import com.student.student.Model.SubjectModel;
import com.student.student.Repository.BranchRepository;
import com.student.student.Repository.FacultyRepository;
import com.student.student.Repository.SubjectRepository;
import com.student.student.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Override
    public String createSubject(SubjectModel subjectModel) {
        Optional<SubjectEntity> subject = subjectRepository.findById(subjectModel.getSubjectCode());
        if(subject.isEmpty()){
            if(null != subjectModel.getSubjectCode() && null != subjectModel.getName()){
                SubjectEntity subjectEntity = new SubjectEntity();
                subjectEntity.setSubjectCode(subjectModel.getSubjectCode());
                subjectEntity.setName(subjectModel.getName());
                if (null != subjectModel.getFacultyDetails()){
                    FacultyModel facultyModels = subjectModel.getFacultyDetails();
                        FacultyEntity facultyEntity = new FacultyEntity();
                        facultyEntity.setId(facultyModels.getId());
                        facultyEntity.setName(facultyModels.getName());
                        facultyEntity.setPhoneNo(facultyModels.getPhoneNo());
                        facultyEntity.setSalary(facultyModels.getSalary());
                    facultyRepository.save(facultyEntity);
                }
                subjectRepository.save(subjectEntity);
                return "subject created";
            }
            else {
                throw new InvalidDetailsException("please enter valid details");
            }
        }
        throw new AlreadyExistsException("subject already existed");
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
            if(null != branch.get().getSubjectDetails()){
                Set<SubjectEntity> subjectEntities = branch.get().getSubjectDetails();
                List<SubjectModel> subjectModels = new ArrayList<>();
                for (SubjectEntity subject : subjectEntities){
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
            FacultyEntity facultyEntities = subjectEntity.get().getFacultyDetails();
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
                facultyEntities = facultyEntity;
            }
            subjectEntity.get().setFacultyDetails(facultyEntities);
            subjectEntities.add(subjectEntity.get());
            subjectRepository.saveAll(subjectEntities);
            return "Updated";
        }
        throw new DoesnotExitsException("there is no such subject");
    }

    @Override
    public String removeFacultyFromSubject(String subjectCode, String facultyCode) {
        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(subjectCode);
        if (subjectEntity.isPresent()) {
            FacultyEntity faculty = subjectEntity.get().getFacultyDetails();
                if (facultyCode.equals(faculty.getId())) {
                    subjectEntity.get().setFacultyDetails(null);
                    subjectRepository.save(subjectEntity.get());
                    facultyRepository.delete(faculty);
                    return "deleted";
            }
        }
        throw new DoesnotExitsException("there is no such faculty");
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
                throw new FailedException("unable to delete subject");
            }
        }
        throw new DoesnotExitsException("there is no such subject");
    }

    @Override
    public BranchModel getBranchForSubject(String subjectCode) {
        Optional<SubjectEntity> subject = subjectRepository.findById(subjectCode);
        if(subject.isPresent()){
           BranchEntity branch = subject.get().getBranchDetails();
            if(null != subject.get().getBranchDetails()){
                    BranchModel branchModel = new BranchModel();
                    branchModel.setBranchCode(branch.getBranchCode());
                    branchModel.setName(branch.getName());
                return branchModel;
            }
            throw new DoesnotExitsException("Branch doesn't exist");
        }
        throw new DoesnotExitsException("Subject doesn't exist");
    }
}
