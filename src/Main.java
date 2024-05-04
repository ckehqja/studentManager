import model.Grade;
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

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {

    public static void main(String[] args) {
        SubjectService subjectService = new SubjectService();
        StudentService studentService = new StudentService();
        ScoreService scoreService = new ScoreService();

        Scanner sc = new Scanner(System.in);
        String mainInput;
        String studentInput;
        String scoreInput;

        init(subjectService, studentService, scoreService);

        System.out.println("Student Manager");

        while (true) {
            subjectService.printSubjectList();

            System.out.println("1. 수강생 관리");
            System.out.println("2. 점수 관리");
            System.out.println("3. 종료");
            mainInput = sc.nextLine();

            switch (mainInput) {
                case "1" -> {
                    System.out.println("1. 수강생 등록");
                    System.out.println("2. 수강생 목록");
                    System.out.println("3. 뒤로가기");
                    studentInput = sc.nextLine();
                    switch (studentInput) {
                        case "1" -> studentService.addStudent(sc);
                        case "2" -> studentService.printStudentList();
                        case "3" -> {
                            return;
                        }
                    }
                }

                case "2" -> {
                    System.out.println("1. 수강생 과목별 시험 최차 및 점수 수정");
                    System.out.println("2. 수강생 과목별 회차 점수 수정");
                    System.out.println("3. 수강생 과목 회차별 등급 조회");
                    System.out.println();
                    studentInput = sc.nextLine();
                    switch (studentInput) {
                        case "1" -> scoreService.addScore(sc);
                        case "2" -> System.out.println();
                        case "3" -> System.out.println();
                        case "4" -> {
                            return;
                        }
                        default -> System.out.println("다시 입력하세요");
                    }
                }

                case "3" -> {
                    System.out.println("3");
                    return;
                }
            }
        }
    }

    public static void init(SubjectService subjectService, StudentService studentService, ScoreService scoreService) {
        subjectService.save(new Subject("Java", REQUIRED));
        subjectService.save(new Subject("객체지향", REQUIRED));
        subjectService.save(new Subject("Spring", REQUIRED));
        subjectService.save(new Subject("JPA", REQUIRED));
        subjectService.save(new Subject("MySQL", REQUIRED));

        subjectService.save(new Subject("디자인과 패턴", OPTION));
        subjectService.save(new Subject("Spring Security", OPTION));
        subjectService.save(new Subject("Redis", OPTION));
        subjectService.save(new Subject("MongoDB", OPTION));

        studentService.save(new Student("박진성", Set.of("Java", "Spring", "JPA", "MySQL", "Redis", "디자인과 패턴")));
        studentService.save(new Student("김근영", Set.of("Java", "객체지향", "JPA", "MySQL", "MongoDB", "Spring Security")));
        studentService.save(new Student("구자준", Set.of("Java", "Spring", "JPA", "MySQL", "Redis", "디자인과 패턴")));
        studentService.save(new Student("조승현", Set.of("객체지향", "Spring", "JPA", "MySQL", "MongoDB", "디자인과 패턴")));
        studentService.save(new Student("우동수", Set.of("Java", "Spring", "JPA", "MySQL", "Redis", "Spring Security")));

        int saveId = scoreService.save(new Score(0, 0));
        Score findScore = scoreService.findById(saveId);
        for (int i = 0; i < 10; i++) {
            int n = (int) (Math.random() * 100);
            findScore.getScore()[i] = n;
            Grade grade = scoreService.setGrade(subjectService.findById(0), n);
            findScore.getGrade()[i] = grade;
        }
    }
}