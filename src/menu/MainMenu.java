package menu;

import model.Score;
import model.Student;
import model.Subject;
import service.ScoreService;
import service.StudentService;
import service.SubjectService;

import java.util.Scanner;
import java.util.Set;

import static model.SubjectType.OPTION;
import static model.SubjectType.REQUIRED;

public class MainMenu {

    static final String SUBJECT_ID = "과목 아이디";

    public static void display() {

        Scanner sc = new Scanner(System.in);
        String mainInput;


        SubjectService subjectService = new SubjectService();
        StudentService studentService = new StudentService();
        ScoreService scoreService = new ScoreService();

        init(subjectService, studentService, scoreService);

        while (true) {
            System.out.println("=======Student Manager=======");
            System.out.println("1. 수강생 관리");
            System.out.println("2. 점수 관리");
            System.out.println("3. 종료");
            System.out.println("4. 모든 점수 출력");
            mainInput = sc.nextLine();
            loopA:
            try {
            while (true) {
                    switch (mainInput) {
                        case "1" -> {
                            StudentMenu.displayMenu();
                        }
                        case "2" -> {
                            ScoreMenu.displayMenu();
                        }

                        case "3" -> {
                            return;
                        }
                        case "4" -> {
                            scoreService.allPrintScore();
                            break loopA;
                        }
                    }
            }
            }catch (Exception e) {}
        }
    }

    public static int inputIntId(Scanner sc, String idName) {
        String input;
        while (true) {
            System.out.println(idName + "를 입력하세요");
            input = sc.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("다시 입력하세요!!!");
            }
        }
    }

    //초기 테스트 데이터
    public static void init(SubjectService subjectService, StudentService studentService, ScoreService scoreService) {
        System.out.println("초기값 생성");

        subjectService.save(new Subject("Java", REQUIRED));
        subjectService.save(new Subject("객체지향", REQUIRED));
        subjectService.save(new Subject("Spring", REQUIRED));
        subjectService.save(new Subject("JPA", REQUIRED));
        subjectService.save(new Subject("MySQL", REQUIRED));

        subjectService.save(new Subject("디자인과 패턴", OPTION));
        subjectService.save(new Subject("Spring Security", OPTION));
        subjectService.save(new Subject("Redis", OPTION));
        subjectService.save(new Subject("MongoDB", OPTION));

        studentService.save(new Student("서찬원", Set.of("Java", "Spring", "JPA", "MySQL", "Redis", "디자인과 패턴")));
        studentService.save(new Student("박세미", Set.of("Java", "객체지향", "JPA", "MySQL", "MongoDB", "Spring Security")));
        studentService.save(new Student("박성균", Set.of("Java", "Spring", "JPA", "MySQL", "Redis", "디자인과 패턴")));
        studentService.save(new Student("차도범", Set.of("객체지향", "Spring", "JPA", "MySQL", "MongoDB", "디자인과 패턴")));
        studentService.save(new Student("이근수", Set.of("Java", "Spring", "JPA", "MySQL", "Redis", "Spring Security")));

        subjectService.printSubjectList();

        for (int i = 0; i < 5; i++) {//사용자 아이디
            Student findStudent = studentService.findById(i);
            Set<String> subjectSet = findStudent.getSubjectSet();

            for (int j = 0; j < 9; j++) {//과목 아이디
                Subject findSubject = subjectService.findById(j);
                for (String subjectName : subjectSet) {
                    if (subjectName.equals(findSubject.getSubjectName())) {
                        scoreService.save(new Score(i, j));
                        Score findScore = scoreService.findBy2Id(i, j);
                        for (int k = 1; k < 11; k++) {//회차
                            int mark = (int) (Math.random() * 40) + 50;
                            scoreService.setStepScore(findScore, k, mark);
                        }
                    }
                }
                int status = (int) (Math.random() * 3) + 1;
                studentService.setStudentStatus(i, status + "");
            }
        }
    }
}
