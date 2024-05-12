package menu;

import model.Student;
import service.StudentService;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static menu.MainMenu.inputIntId;

public class StudentMenu {
    static final String STUDENT_ID = "수강생 아이디";

    public static void displayMenu() throws IllegalAccessException {
        int studentId;
        String studentName;
        Set<String> subjectSet = new HashSet<>();
        String studentListInput;
        String status;
        String studentInput;
        Scanner sc = new Scanner(System.in);
        StudentService studentService = new StudentService();

        System.out.println("=======수강생 관리=======");
        System.out.println("1. 수강생 등록");
        System.out.println("2. 수강생 목록");
        System.out.println("0. 뒤로가기");
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
            case "2" -> {
                loopB:
                while (true) {

                    studentService.printStudentList();
                    System.out.println();

                    System.out.println("1. 수강생 상태 업데이트");
                    System.out.println("2. 수강생 정보 조회");
                    System.out.println("3. 수강생 정보 수정");
                    System.out.println("4. 상태별 수강생 목록 조회");
                    System.out.println("5. 수강생 삭제");
                    System.out.println("0. 뒤로가기");
                    studentListInput = sc.nextLine();
                    switch (studentListInput) {
                        case "1", "2" -> {
                            studentId = inputIntId(sc, STUDENT_ID);
                            if (studentListInput.equals("1")) {
                                System.out.println("수강생 상태 숫자 입력(1.green, 2.red, 3.yellow)>");
                                status = sc.nextLine();
                                studentService.setStudentStatus(studentId, status);

                            } else {
                                studentService.printStudent(studentId);
                            }
                        }
                        case "3" -> {
                            studentId = inputIntId(sc, STUDENT_ID);
                            System.out.println("이름 또는 수정할 상태(1.green, 2.red, 3.yellow)를 적으세요>");
                            String nameOrStatusInput = sc.nextLine();
                            switch (nameOrStatusInput) {
                                case "1", "2", "3" -> studentService.setStudentStatus(studentId, nameOrStatusInput);
                                default -> studentService.findById(studentId).setStudentName(nameOrStatusInput);
                            }
                        }
                        case "4" -> {
                            System.out.println("이름 또는 수정할 상태(1.green, 2.red, 3.yellow)를 적으세요>");
                            System.out.println("출력하고 싶은 상태 입력>");
                            status = sc.nextLine();
                            studentService.printStudentByStatus(status);

                        }
                        case "5" -> {
                            studentId = inputIntId(sc, STUDENT_ID);
                            System.out.println("진짜로 지우겠습니까(y/n)>");
                            if (sc.nextLine().equals("y")) {
                                studentService.delete(studentId);
                            }
                        }
                        case "0" -> {
                            break loopB;
                        }
                    }
                }
            }
            case "0" -> {
                throw new IllegalAccessException("뒤로가기");
            }
        }
    }
}


