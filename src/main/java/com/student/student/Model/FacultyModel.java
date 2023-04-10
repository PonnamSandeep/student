package com.student.student.Model;

import com.student.student.Entity.SubjectEntity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter@Setter
public class FacultyModel {
    private String id;
    private String name;
    private String phoneNo;
    private String salary;
    private Set<SubjectModel> subjects;

}
