package com.student.student.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Set;

@Entity
public class FacultyEntity {
    @Id
    private String id;
    private String name;
    private String phoneNo;
    private String salary;
    @OneToMany(mappedBy = "facultyDetails")
    private Set<SubjectEntity> subjects;

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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Set<SubjectEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectEntity> subjects) {
        this.subjects = subjects;
    }
}
