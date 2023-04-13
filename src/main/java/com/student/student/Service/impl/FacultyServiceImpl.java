package com.student.student.Service.impl;

import com.student.student.Entity.*;
import com.student.student.Exception.*;
import com.student.student.Model.*;
import com.student.student.Repository.BranchRepository;
import com.student.student.Repository.FacultyRepository;
import com.student.student.Repository.SubjectRepository;
import com.student.student.Service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public String createFaculty(FacultyModel facultyModel) {
        Optional<FacultyEntity> faculty = facultyRepository.findById(facultyModel.getId());
        if (faculty.isEmpty()) {
            if (null != facultyModel.getId() && null != facultyModel.getName()) {
                FacultyEntity facultyEntity = new FacultyEntity();
                facultyEntity.setId(facultyModel.getId());
                facultyEntity.setName(facultyModel.getName());
                facultyEntity.setPhoneNo(facultyModel.getPhoneNo());
                facultyEntity.setSalary(facultyModel.getSalary());
                if (null != facultyModel.getSubjects()) {
                    Set<SubjectModel> subjects = facultyModel.getSubjects();
                    Set<SubjectEntity> subjectEntities = new HashSet<>();
                    for (SubjectModel subject : subjects) {
                        SubjectEntity subjectEntity = new SubjectEntity();
                        subjectEntity.setSubjectCode(subject.getSubjectCode());
                        subjectEntity.setName(subject.getName());
                        subjectEntities.add(subjectEntity);
                    }
                    facultyEntity.setSubjects(subjectEntities);
                    subjectRepository.saveAll(subjectEntities);
                }
                facultyRepository.save(facultyEntity);
                return "faculty created";
            } else {
                throw new InvalidDetailsException("please enter valid details");
            }
        }
        throw new AlreadyExistsException("faculty already existed");
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
    public List<FacultyModel> getFacultyForBranch(String branchCode) {
        Optional<BranchEntity> branch = branchRepository.findById(branchCode);
        if (branch.isPresent()) {
            Set<SubjectEntity> subjectDetails = branch.get().getSubjectDetails();
            if (null != subjectDetails) {
                List<FacultyModel> facultyModels = new ArrayList<>();
                for (SubjectEntity subject : subjectDetails) {
                    SubjectModel subjectModel = new SubjectModel();
                    subjectModel.setSubjectCode(subject.getSubjectCode());
                    subjectModel.setName(subject.getName());
                    if (isFacultyAdded(facultyModels,subject.getFacultyDetails().getId()))
                    {
                        for(FacultyModel facultyModel : facultyModels){
                            if (subject.getFacultyDetails().getId().equals(facultyModel.getId())){
                                facultyModel.getSubjects().add(subjectModel);
                            }
                        }
                    }
                    else {
                    FacultyEntity faculty = subject.getFacultyDetails();
                    FacultyModel facultyModel = new FacultyModel();
                    facultyModel.setId(faculty.getId());
                    facultyModel.setName(faculty.getName());
                    facultyModel.setPhoneNo(faculty.getPhoneNo());
                    facultyModel.setSalary(faculty.getSalary());
                    facultyModel.setSubjects(Set.of(subjectModel));
                    facultyModels.add(facultyModel);
                    }
                }
                return facultyModels;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String updateSubjectForFaculty(SubjectModel subjectModel, String Id) {
        Optional<FacultyEntity> facultyEntity = facultyRepository.findById(Id);
        if (facultyEntity.isPresent()) {
            Set<SubjectEntity> subjectEntities = facultyEntity.get().getSubjects();
            Set<FacultyEntity> facultyEntities = new HashSet<>();
            SubjectEntity subjectEntity = new SubjectEntity();
            if (null != subjectModel.getSubjectCode()) {
                subjectEntity.setSubjectCode(subjectModel.getSubjectCode());
            }
            if (null != subjectModel.getName()) {
                subjectEntity.setName(subjectModel.getName());
                subjectEntities.add(subjectEntity);
            }
            facultyEntity.get().setSubjects(subjectEntities);
            facultyEntities.add(facultyEntity.get());
            facultyRepository.saveAll(facultyEntities);
        }
        throw new DoesnotExitsException("there is no such faculty");
    }

    @Override
    public String removeSubjectForFaculty(String facultyId, String subjectCode) {
        Optional<FacultyEntity> facultyEntity = facultyRepository.findById(facultyId);
        if (facultyEntity.isPresent()) {
            Set<SubjectEntity> subjectDetails = facultyEntity.get().getSubjects();
            for (SubjectEntity subject : subjectDetails) {
                if (subjectCode.equals(subject.getSubjectCode())) {
                    subjectRepository.delete(subject);
                }
            }
        }
        throw new DoesnotExitsException("faculty doesn't exist");
    }

    @Override
    public String removeFaculty(String Id) {
        if (null != Id) {
            Optional<FacultyEntity> facultyEntity = facultyRepository.findById(Id);
            facultyRepository.delete(facultyEntity.get());
            if (facultyRepository.findById(Id).isEmpty()) {
                return "faculty deleted from college";
            } else {
                throw new FailedException("unable to delete faculty");
            }
        }
        throw new DoesnotExitsException("there is no such faculty in college");
    }

    @Override
    public ResponseEntity<Object> getSubjectsForFaculty(String Id) {
        Optional<FacultyEntity> faculty = facultyRepository.findById(Id);
        if (faculty.isPresent()) {
            Set<SubjectEntity> facultyEntities = faculty.get().getSubjects();
            if (null != faculty.get().getSubjects()) {
                Set<SubjectModel> subjectModels = new HashSet<>();
                for (SubjectEntity subject : facultyEntities) {
                    SubjectModel subjectModel = new SubjectModel();
                    subjectModel.setSubjectCode(subject.getSubjectCode());
                    subjectModel.setName(subject.getName());
                    subjectModels.add(subjectModel);
                }
                return new ResponseEntity<>(subjectModels, HttpStatus.OK);
            }
            return null;
        }
        return null;
    }

    @Override
    public List<StudentModel> getStudentsForFaculty(String code) {
        Optional<BranchEntity> faculty = branchRepository.findById(code);
        if (faculty.isPresent()) {
            List<StudentEntity> studentEntities = faculty.get().getStudentsDetails();
            if (null != faculty.get().getStudentsDetails()) {
                List<StudentModel> studentModels = new ArrayList<>();
                for (StudentEntity student : studentEntities) {
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
    private boolean isFacultyAdded(List<FacultyModel> facultyModels, String facultyId){
        boolean isExists = false;
        for (FacultyModel facultyModel : facultyModels){
            if (facultyId.equals(facultyModel.getId())){
                isExists = true;
                break;
            }
        }
        return isExists;
    }
}
