import model.Score;
import model.Student;
import model.Subject;
import service.ScoreService;
import service.StudentService;
import service.SubjectService;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static model.SubjectType.OPTION;
import static model.SubjectType.REQUIRED;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {

    public static void main(String[] args) {

        String studentName;
        Set<String> subjectSet = new HashSet<>();

        int studentId;
        int subjectId;
        int step;
        int mark;

        Scanner sc = new Scanner(System.in);
        String mainInput;
        String studentInput;

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
            while (true) {
                switch (mainInput) {
                    case "1" -> {
                        System.out.println("=======수강생 관리=======");
                        System.out.println("1. 수강생 등록");
                        System.out.println("2. 수강생 목록");
                        System.out.println("3. 뒤로가기");
                        studentInput = sc.nextLine();
                        switch (studentInput) {
                            case "1" -> {
                                System.out.println("수강생 이름 >");
                                studentName = sc.nextLine();
                                boolean next = true;
                                int requiredNum;
                                while (subjectSet.size() < 3 || next) {
                                    System.out.println("필수 3개 이상(번호 입력, 다음 n 입력)");
                                    System.out.println("1. Java 2. 객체지향, 3. Spring, 4. JPA, 5. MySQL");
                                    switch (sc.nextLine()) {
                                        case "1" -> subjectSet.add("Java");
                                        case "2" -> subjectSet.add("객체지향");
                                        case "3" -> subjectSet.add("Spring");
                                        case "4" -> subjectSet.add("Jpa");
                                        case "5" -> subjectSet.add("MySQL");
                                        case "n" -> next = false;
                                        default -> System.out.println("잘못된 입력입니다. ");
                                    }
                                }
                                next = true;
                                requiredNum = subjectSet.size();

                                while (subjectSet.size() - requiredNum < 2 || next) {
                                    System.out.println("선택 2개 이상(번호 입력, 다음 n 입력)");
                                    System.out.println("1. 디자인 패턴, 2. Spring Security, 3. Redis, 4. MongoDB");
                                    switch (sc.nextLine()) {
                                        case "1" -> subjectSet.add("디자인 패턴");
                                        case "2" -> subjectSet.add("Spring Security");
                                        case "3" -> subjectSet.add("Redis");
                                        case "4" -> subjectSet.add("MongoDB");
                                        case "n" -> next = false;
                                        default -> System.out.println("잘못된 입력입니다.");
                                    }
                                }
                                int saveStudentId = studentService.save(new Student(studentName, subjectSet));
                                System.out.println(saveStudentId + ". " + studentName + "  등록되었습니다.");
                            }
                            case "2" -> studentService.printStudentList();
                            case "3" -> {
                                break loopA;
                            }
                        }
                    }

                    case "2" -> {
                        System.out.println("=======점수 관리=======");
                        System.out.println("1. 수강생 과목별 시험 최차 및 점수 등록");
                        System.out.println("2. 수강생 과목별 회차 점수 수정");
                        System.out.println("3. 수강생 과목 회차별 등급 조회");
                        System.out.println("4. 뒤로가기");
                        System.out.println();
                        studentInput = sc.nextLine();
                        switch (studentInput) {

                            case "1", "2" -> {
                                System.out.println("학생 아이디 입력");
                                studentId = Integer.parseInt(sc.nextLine());
                                System.out.println("과목 아이디 입력");
                                subjectId = Integer.parseInt(sc.nextLine());

                                while(true) {
                                    System.out.println("1~10회차 입력");
                                    step = Integer.parseInt(sc.nextLine());
                                    if (step >= 1 && step <= 10) break;
                                    else System.out.println("다시 입력!!!");
                                }
                                while(true) {
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
                                System.out.println("studentId >");
                                studentId = Integer.parseInt(sc.nextLine());
                                System.out.println("subjectId >");
                                subjectId = Integer.parseInt(sc.nextLine());
                                Subject findSubject = subjectService.findById(subjectId);

                                System.out.println(studentService.findById(studentId).getStudentName()
                                        + " - " + findSubject.getSubjectName()
                                        + " : " + findSubject.getSubjectType());
                                Score findScore = scoreService.findBy2Id(studentId, subjectId);

                                scoreService.printScore(findScore);
                            }
                            case "4" -> {
                                break loopA;
                            }
                        }
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
            }
        }

    }
}