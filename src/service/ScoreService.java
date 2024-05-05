package service;

import model.*;
import repository.ScoreRepository;

import java.util.List;
import java.util.Scanner;

import static model.Grade.*;

public class ScoreService implements Service<Score> {

    private static final ScoreRepository repository = new ScoreRepository();
    private static final StudentService studentService = new StudentService();
    private static final SubjectService subjectService = new SubjectService();

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
        System.out.print("점수");
        for (int mark : score.getMarkArr()) {
//            System.out.printf("%5d", mark);
            System.out.print("   " + mark);
        }
        System.out.println();
        System.out.print("등급   ");
        for (Grade grade : score.getGradeArr()) {
            if(grade == null) System.out.print("x    ");
            else System.out.print(grade.getGrade() + "    ");
        }
        System.out.println();
        System.out.println();
    }

    public void addScore(Scanner sc) {
        int studentId = 0;
        int subjectId = 0;
        int step;
        int mark;
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
                            mark = Integer.parseInt(sc.nextLine());
                            if (mark >= 0 && mark <= 100) break;
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
            int saveId = repository.save(new Score(studentId, subjectId));
            Score findScore = repository.findById(saveId);
            setStepScore(findScore, step, mark);
            System.out.println("새로운 점수를 만들어 추가하였습니다.");
        } else {
            for (Score score : list) {
                if (score.getSubjectId() == findSubject.getId() &&
                        score.getStudentId() == findStudent.getId()) {
                    score.getMarkArr()[step - 1] = mark;
                    score.getGradeArr()[step - 1] = setGrade(subjectId, mark);
                    System.out.println("기존 점수에 추가하였습니다. ");
                } else {
                    int saveId = repository.save(new Score(studentId, subjectId));
                    Score findScore = repository.findById(saveId);
                    findScore.getMarkArr()[step - 1] = mark;
                    findScore.getGradeArr()[step - 1] = setGrade(subjectId, mark);
                    System.out.println("새로운 점수를 만들어 추가하였습니다.");
                }
            }
        }
    }

    public void editScore(int studentId, int subjectId, int step, int mark) {
        List<Score> scoreList = repository.getList();
        for (Score inScore : scoreList) {
            if (inScore.getStudentId() == studentId && inScore.getSubjectId() == subjectId) {
                setStepScore(inScore, step, mark);
            }
        }
    }

    public Grade setGrade(int subjectId, int mark) {
        Subject findSubject = subjectService.findById(subjectId);
        if (findSubject.getSubjectType() == SubjectType.REQUIRED) {
            if (mark >= 95) return A;
            else if (mark >= 90) return B;
            else if (mark >= 80) return C;
            else if (mark >= 70) return D;
            else if (mark >= 60) return F;
            else return N;
        } else {
            if (mark >= 90) return A;
            else if (mark >= 80) return B;
            else if (mark >= 70) return C;
            else if (mark >= 60) return D;
            else if (mark >= 50) return F;
            else return N;
        }
    }

    public void setStepScore(Score score, int step, int mark) {
        score.getMarkArr()[step - 1] = mark;
        score.getGradeArr()[step - 1] = setGrade(score.getSubjectId(), mark);
    }

    //테스트를 위한 출력
    public void allPrintScore() {
        System.out.println("모든 점수 출력");
        List<Score> list = repository.getList();
        if (list.isEmpty()) {
            System.out.println("list.empty");
        }
        for (Score score : list) {
            int studentId = score.getStudentId();
            Student findStudent = studentService.findById(studentId);
            int subjectId = score.getSubjectId();
            Subject findSubject = subjectService.findById(subjectId);
            System.out.println(findStudent.getStudentName() + " - " + findSubject.getSubjectName()
                    + " - " + findSubject.getSubjectType());
            printScore(score);
        }
    }
}
