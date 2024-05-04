package model;

public class Subject implements AutoId{
    private static int staticId = 0;
     private int subjectId;
    private String subjectName;
    private SubjectType subjectType;

    public Subject(String subjectName, SubjectType subjectType) {
        this.subjectName = subjectName;
        this.subjectType = subjectType;
    }

    @Override
    public void creatAutoId() {
        subjectId = staticId++;
    }

    @Override
    public int getId() {
        return subjectId;
    }
}
