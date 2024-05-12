package model;

import lombok.Getter;
import lombok.Setter;
import model.enums.StudentStatus;

import java.util.Set;

@Getter
public class Student implements CreateModel {
    private static int staticId = 0;
    private int studentId;
    @Setter
    private String studentName;
    private final Set<String> subjectSet;
    @Setter
    private StudentStatus studentStatus;//기본값 지정

    public Student(String studentName, Set<String> subjectSet) {
        this.studentName = studentName;
        this.subjectSet = subjectSet;
        this.studentStatus = StudentStatus.GREEN;
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
