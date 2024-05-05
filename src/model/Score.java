package model;

import lombok.Getter;

@Getter
public class Score implements CreateModel {
    private int subjectId;
    private int studentId;
    private final int STEP = 10;//1~10
    private int[] score = new int[STEP];//0~100
    private Grade[] grade = new Grade[STEP];

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
