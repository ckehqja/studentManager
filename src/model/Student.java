package model;

import lombok.Getter;

import java.util.Set;

@Getter
public class Student implements CreateModel {
    private static int staticId = 0;
    private int studentId;
    private  String studentName;
    private  Set<String> subjectSet ;

    public Student(String studentName, Set<String> subjectSet) {
        this.studentName = studentName;
        this.subjectSet = subjectSet;
    }

    @Override
    public void create() {
        studentId = staticId++;
    }

    @Override
    public int getId() {
        return studentId;
    }
}
