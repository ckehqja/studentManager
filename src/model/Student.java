package model;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Student implements AutoId {
    private static int staticId = 0;
    private int studentId;
    private String studentName;
    private Set<String> subjectSet = new HashSet<>();

    public Student(String studentName, Set<String> subjectSet) {
        this.studentName = studentName;
        this.subjectSet = subjectSet;
    }

    @Override
    public void creatAutoId() {
        studentId = staticId++;
    }

    @Override
    public int getId() {
        return studentId;
    }
}
