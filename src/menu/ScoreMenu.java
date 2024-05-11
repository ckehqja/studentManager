package menu;

import model.Score;
import model.Subject;
import service.ScoreService;
import service.StudentService;
import service.SubjectService;

import java.util.Scanner;

import static menu.MainMenu.inputIntId;

public class ScoreMenu {
    static final String STUDENT_ID = "수강생 아이디";
    static final String SUBJECT_ID = "과목 아이디";


    public static void displayMenu() throws IllegalAccessException {


        int studentId;
        int subjectId;
        int step;
        int mark;

        Scanner sc = new Scanner(System.in);
        String studentInput;


        SubjectService subjectService = new SubjectService();
        StudentService studentService = new StudentService();
        ScoreService scoreService = new ScoreService();


        System.out.println("=======점수 관리=======");
        System.out.println("1. 수강생 과목별 시험 최차 및 점수 등록");
        System.out.println("2. 수강생 과목별 회차 점수 수정");
        System.out.println("3. 수강생 과목 회차별 등급 조회");
        System.out.println("0. 뒤로가기");
        System.out.println();
        studentInput = sc.nextLine();
        switch (studentInput) {

            case "1", "2" -> {
                studentId = inputIntId(sc, STUDENT_ID);
                subjectId = inputIntId(sc, SUBJECT_ID);

                while (true) {
                    System.out.println("1~10회차 입력");
                    step = Integer.parseInt(sc.nextLine());
                    if (step >= 1 && step <= 10) break;
                    else System.out.println("다시 입력!!!");
                }
                while (true) {
                    System.out.println("점수 입력");
                    mark = Integer.parseInt(sc.nextLine());
                    if (mark >= 0 && mark <= 100) break;
                    else System.out.println("다시 입력!!!");
                }
                if (studentInput.equals("1")) {
                    scoreService.addScore(studentId, subjectId, step, mark);
                } else {
                    scoreService.editScore(studentId, subjectId, step, mark);
                }
            }
            case "3" -> {
                studentId = inputIntId(sc, STUDENT_ID);
                subjectId = inputIntId(sc, SUBJECT_ID);
                Subject findSubject = subjectService.findById(subjectId);

                System.out.println(studentService.findById(studentId).getStudentName()
                        + " - " + findSubject.getSubjectName()
                        + " : " + findSubject.getSubjectType());
                Score findScore = scoreService.findBy2Id(studentId, subjectId);

                scoreService.printScore(findScore);
            }
            case "0" -> {
                throw new IllegalAccessException("뒤로가기");
            }
        }
    }

}
