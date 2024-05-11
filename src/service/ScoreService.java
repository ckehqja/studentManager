package service;

import model.*;
import repository.ScoreRepository;

import java.util.List;

import static model.Grade.*;

public class ScoreService implements Service<Score> {

    private final ScoreRepository repository = new ScoreRepository();
    private final StudentService studentService = new StudentService();
    private final SubjectService subjectService = new SubjectService();

    @Override
    public int save(Score score) {
        return repository.save(score);
    }

    @Override
    public Score findById(int id) {
        return repository.findById(id);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    public Score findBy2Id(int studentId, int subjectId) {
        return repository.findBy2Id(studentId, subjectId);
    }

    public void printScore(Score score) {
        System.out.print("회차");
        for (int i = 1; i < 11; i++) {
            System.out.printf("%5d", i);
        }
        System.out.println();
        System.out.print("점수");
        for (int mark : score.getMarkArr()) {
            System.out.printf("%5d", mark);
        }
        System.out.println();
        System.out.print("등급");
        for (Grade grade : score.getGradeArr()) {
            if (grade == null) System.out.printf("%5c", 'x');
            else System.out.printf("%5c", grade.getGrade());
        }
        System.out.println();
        System.out.println();
    }

    public void addScore(int studentId, int subjectId, int step, int mark) {
        Score findScore = repository.findBy2Id(studentId, subjectId);
        if (findScore == null) {
            Score score = new Score(studentId, subjectId);
            save(score);
            setStepScore(score, step, mark);
            System.out.println("새로운 점수를 만듭니다.");
        } else if (findScore.getGradeArr()[step - 1] == null) {
            setStepScore(findScore, step, mark);
            System.out.println("기존 점수에서 추가합니다.");
        } else {
            System.out.println("이미 점수를 추가했습니다. 변경에서 수정하세요");
        }
    }

    public void editScore(int studentId, int subjectId, int step, int mark) {
        boolean display = true;
        List<Score> scoreList = repository.getList();
        for (Score inScore : scoreList) {
            if (inScore.getStudentId() == studentId && inScore.getSubjectId() == subjectId) {
                setStepScore(inScore, step, mark);
                display = false;
            }
        }
        if(display) System.out.println("존재하지 않는 수강생 아이디 또는 과목 아이디 !!");
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
