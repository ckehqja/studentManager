package model;

import lombok.Getter;

import static model.Grade.*;

@Getter
public class Score implements CreateModel {
    private int subjectId;
    private int studentId;
    private final int STEP = 10;//1~10
    private int[] score = new int[STEP];//0~100
    private Grade[] grade = new Grade[STEP];

    @Override
    public void create() {
    }

    @Override
    public int getId() {
        return 0;
    }

    public Grade setRequiredGrade(int score) {
        if (score >= 95) return A;
        else if (score >= 90) return B;
        else if (score >= 80) return C;
        else if (score >= 70) return D;
        else if (score >= 60) return F;
        else return N;
    }

    public Grade setOptionGrade(int score) {
        if (score >= 90) return A;
        else if (score >= 80) return B;
        else if (score >= 70) return C;
        else if (score >= 60) return D;
        else if (score >= 50) return F;
        else return N;
    }
}
