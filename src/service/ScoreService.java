package service;

import model.*;
import repository.ScoreRepository;

import java.util.List;
import java.util.Scanner;

import static model.Grade.*;

public class ScoreService implements Service<Score> {

    static ScoreRepository repository = new ScoreRepository();
    StudentService studentService = new StudentService();
    SubjectService subjectService = new SubjectService();

    @Override
    public int save(Score score) {
        return repository.save(score);
    }

    @Override
    public Score findById(int id) {
        return repository.findById(id);
    }

    public Score findBy2Id(int studentId, int subjectId) {
        return repository.findBy2Id(studentId, subjectId);
    }

    public void printScore(Score score) {
        System.out.print("회차   ");
        for (int i = 1; i < 11; i++) {
            System.out.print(i + "    ");
        }
        System.out.println();
        System.out.print("점수   ");
        for (int scoreO : score.getScore()) {
            System.out.print(scoreO + "   ");
        }
        System.out.println();
        System.out.print("등급   ");
        for (Grade grade : score.getGrade()) {
            System.out.print(grade.getGrade() + "    ");
        }
        System.out.println();
        System.out.println();
    }

    public void addScore(Scanner sc) {
        int studentId = 0;
        int subjectId = 0;
        int step;
        int score;
        Student findStudent = null;
        Subject findSubject = null;
        while (true) {
            System.out.println("수강생 아이디를 입력하세요>");
            try {
                studentId = Integer.parseInt(sc.nextLine());
                System.out.println(studentId);
                findStudent = studentService.findById(studentId);
            } catch (NumberFormatException e) {
                System.out.println("정수만 입력하세요 !!!");
            }
            if (findStudent != null) {
                System.out.println(findStudent.getStudentName() + " 수강생 입니다.");
                break;
            }
            System.out.println("잘못된 값입니다.");
        }
        while (true) {
            System.out.println("과목 아이디를 입력하세요>");
            try {
                subjectId = Integer.parseInt(sc.nextLine());
                findSubject = subjectService.findById(subjectId);
            } catch (NumberFormatException e) {
                System.out.println("정수만 입력하세요 !!!");
            }
            if (findSubject != null) {
                System.out.println(findSubject.getSubjectName() + " 과목입니다.");
                System.out.println("회차(1~10) 범위를 적으세요");
                try {
                    step = Integer.parseInt(sc.nextLine());
                    if (step > 0 && step <= 10) {
                        System.out.println("점수를 입력하세요>");
                        try {
                            score = Integer.parseInt(sc.nextLine());
                            if (score >= 0 && score <= 100) break;
                            else System.out.println("0 ~ 100 사이 정수 입력하세요!!!");
                        } catch (NumberFormatException e) {
                            System.out.println("정수만 입력하세요 !!!");
                        }
                    } else {
                        System.out.println("1 ~ 10 사이 정수 입력하세요!!!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("정수만 입력하세요 !!!");
                }
            }
        }
        List<Score> list = repository.getList();
        if (list.isEmpty()) {
            int saveId = repository.save(new Score(subjectId, studentId));
            Score findScore = repository.findById(saveId);
            setStepScore(findScore, step, score);
            System.out.println("새로운 점수를 만들어 추가하였습니다.");
        } else {
            for (Score listScore : list) {
                if (listScore.getSubjectId() == findSubject.getId() &&
                        listScore.getStudentId() == findStudent.getId()) {
                    listScore.getScore()[step - 1] = score;
                    listScore.getGrade()[step - 1] = setGrade(subjectId, score);
                    System.out.println("기존 점수에 추가하였습니다. ");
                } else {
                    int saveId = repository.save(new Score(subjectId, studentId));
                    Score findScore = repository.findById(saveId);
                    findScore.getScore()[step - 1] = score;
                    findScore.getGrade()[step - 1] = setGrade(subjectId, score);
                    System.out.println("새로운 점수를 만들어 추가하였습니다.");
                }
            }
        }
    }

    public void editScore(int studentId, int subjectId, int step, int score) {
        List<Score> scoreList = repository.getList();
        for (Score inScore : scoreList) {
            if (inScore.getStudentId() == studentId && inScore.getSubjectId() == subjectId) {
                setStepScore(inScore, step, score);
            }
        }
    }

    public Grade setGrade(int subjectId, int score) {
        Subject findSubject = subjectService.findById(subjectId);
        if (findSubject.getSubjectType() == SubjectType.REQUIRED) {
            if (score >= 95) return A;
            else if (score >= 90) return B;
            else if (score >= 80) return C;
            else if (score >= 70) return D;
            else if (score >= 60) return F;
            else return N;
        } else {
            if (score >= 90) return A;
            else if (score >= 80) return B;
            else if (score >= 70) return C;
            else if (score >= 60) return D;
            else if (score >= 50) return F;
            else return N;
        }
    }

    public void setStepScore(Score score, int step, int mark) {
        score.getScore()[step - 1] = mark;
        score.getGrade()[step - 1] = setGrade(score.getSubjectId(), mark);
    }
}
