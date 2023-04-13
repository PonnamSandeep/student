package com.student.student.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Branches")
public class BranchEntity {
    @Id
    private String branchCode;
    private String name;
    @ManyToMany(mappedBy = "branchDetails")
    private List<CollegeEntity> collageDetails;
    @OneToMany(mappedBy = "branchDetails")
    private Set<SubjectEntity> subjectDetails;
    @OneToMany(mappedBy = "branchDetails")
    private List<StudentEntity> studentsDetails;

    public BranchEntity() {
        this.branchCode = branchCode;
        this.name = name;
        this.collageDetails = collageDetails;
        this.subjectDetails = subjectDetails;
        this.studentsDetails = studentsDetails;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CollegeEntity> getCollageDetails() {
        return collageDetails;
    }

    public void setCollageDetails(List<CollegeEntity> collageDetails) {
        this.collageDetails = collageDetails;
    }

    public Set<SubjectEntity> getSubjectDetails() {
        return subjectDetails;
    }

    public void setSubjectDetails(Set<SubjectEntity> subjectDetails) {
        this.subjectDetails = subjectDetails;
    }

    public List<StudentEntity> getStudentsDetails() {
        return studentsDetails;
    }

    public void setStudentsDetails(List<StudentEntity> studentsDetails) {
        this.studentsDetails = studentsDetails;
    }
}
