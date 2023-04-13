package com.student.student.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.List;

@Entity
public class SubjectEntity {
    @Id
    private String subjectCode;
    private String name;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private BranchEntity branchDetails;
    @ManyToOne(cascade = CascadeType.ALL)
    private FacultyEntity facultyDetails;

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BranchEntity getBranchDetails() {
        return branchDetails;
    }

    public void setBranchDetails(BranchEntity branchDetails) {
        this.branchDetails = branchDetails;
    }

    public FacultyEntity getFacultyDetails() {
        return facultyDetails;
    }

    public void setFacultyDetails(FacultyEntity facultyDetails) {
        this.facultyDetails = facultyDetails;
    }
}
