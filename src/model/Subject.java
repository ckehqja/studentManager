package model;

import lombok.Getter;

@Getter
public class Subject implements CreateModel {
    private static int staticId = 0;
     private int subjectId;
    private final String subjectName;
    private final SubjectType subjectType;

    public Subject(String subjectName, SubjectType subjectType) {
        this.subjectName = subjectName;
        this.subjectType = subjectType;
    }

    @Override
    public void create() {
        subjectId = staticId++;
    }

    @Override
    public int getId() {
        return subjectId;
    }
}
