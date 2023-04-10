package com.student.student.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class CollegeEntity {
    @Id
    private String collegeCode;
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<BranchEntity> branchDetails;

    public String getCollageCode() {
        return collegeCode;
    }

    public void setCollageCode(String collageCode) {
        this.collegeCode = collageCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BranchEntity> getBranchDetails() {
        return branchDetails;
    }

    public void setBranchDetails(List<BranchEntity> branchDetails) {
        this.branchDetails = branchDetails;
    }
}
