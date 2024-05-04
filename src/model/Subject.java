package model;

public class Subject implements AutoId{
    static private int subjectId = 0;
    private String subjectName;
    private SubjectType subjectType;

    public Subject(String subjectName, SubjectType subjectType) {
        this.subjectName = subjectName;
        this.subjectType = subjectType;
    }

    @Override
    public void creatAutoId() {
        subjectId++;
    }

    @Override
    public int getId() {
        return subjectId;
    }
}
