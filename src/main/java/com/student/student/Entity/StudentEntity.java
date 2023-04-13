package com.student.student.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    private String id;
    private String name;
    @Column(name = "student_year")
    private String year;
    private String password;
    private String collegeCode;
    @ManyToOne(cascade = CascadeType.ALL)
    private BranchEntity branchDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BranchEntity getBranchDetails() {
        return branchDetails;
    }

    public void setBranchDetails(BranchEntity branchDetails) {
        this.branchDetails = branchDetails;
    }

    public String getCollegeCode() {
        return collegeCode;
    }

    public void setCollegeCode(String collegeCode) {
        this.collegeCode = collegeCode;
    }
}
