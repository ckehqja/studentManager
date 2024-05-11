package service;

import model.*;
import model.enums.Grade;
import model.enums.SubjectType;
import repository.ScoreRepository;

import java.util.List;

import static model.enums.Grade.*;

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

    /**
     *점수를 등록하는 메서드
     * 같은 회차 중복 등록 안되며 회차 순서되로 입력을 해야한다.
     * 주의사항 step 은 항상 -1을 해주어야 한다.
     */
    public void addScore(int studentId, int subjectId, int step, int mark) {
        Score findScore = repository.findBy2Id(studentId, subjectId);

        if (step == 1 && findScore == null) {
            Score score = new Score(studentId, subjectId);
            save(score);
            setStepScore(score, step, mark);
            System.out.println("새로운 점수를 만듭니다.");
        } else if (step == 1) {
            System.out.println("이미 등록외었습니다.");
        } else {
            if (findScore == null) System.out.println("1회차부터 입력하세요!!");
            else if (findScore.getGradeArr()[step - 2] != null
                    && findScore.getGradeArr()[step -1] == null) {
                setStepScore(findScore, step, mark);
                System.out.println("점수를 추가합니다.");
            } else {
                System.out.println("이미 점수를 입력했거나 차례대로 입력하세요!!");
            }
        }
    }

    public void editScore(int studentId, int subjectId, int step, int mark) {
        boolean display = true;
        List<Score> scoreList = repository.getList();
        for (Score inScore : scoreList) {
            if (isScoreSameStudentIdAndSubjectId(studentId, subjectId, inScore)) {
                //1회차를 수정하거나 전회차에 점수가 있는 경우에만 수정 가능
                if (step != 1 || inScore.getGradeArr()[step] != null) {
                    setStepScore(inScore, step, mark);
                } else {
                    System.out.println("점수가 등록된 회차만 수정이 가능합니다.");
                }
                display = false;
            }
        }
        if (display) System.out.println("존재하지 않는 수강생 아이디 또는 과목 아이디 !!");
        else System.out.printf("%d / %d / %d회차 %d점으로 변경되었습니다.", studentId, subjectId, step, mark);
    }


    private static boolean isScoreSameStudentIdAndSubjectId(int studentId, int subjectId, Score inScore) {
        return inScore.getStudentId() == studentId && inScore.getSubjectId() == subjectId;
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
