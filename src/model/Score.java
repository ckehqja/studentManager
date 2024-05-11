package model;

import lombok.Getter;
import model.enums.Grade;

@Getter
public class Score implements CreateModel {
    private final int subjectId;
    private final int studentId;
    private final int STEP = 10;//1~10
    private final int[] markArr = new int[STEP];//0~100
    private final Grade[] gradeArr = new Grade[STEP];

    public Score(int studentId, int subjectId) {
        this.subjectId = subjectId;
        this.studentId = studentId;
    }

    @Override
    public void create() {
    }

    @Override
    public int getId() {
        return 0;
    }
}
