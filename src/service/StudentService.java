package service;

import model.Student;
import repository.Repository;
import repository.StudentRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class StudentService implements Service<Student> {

    private final Repository<Student> repository = new StudentRepository();

    @Override
    public int save(Student student) {
        return repository.save(student);
    }

    @Override
    public Student findById(int id) {
        return repository.findById(id);
    }

    public void printStudentList() {
        List<Student> list = repository.getList();
        System.out.println("수강생 목록");
        for (Student student : list) {
            System.out.println(student.getId() + ". " + student.getStudentName());
        }
        System.out.println();
        System.out.println();
    }

    public void joinStudent(Scanner sc) {
        boolean next;
        int requiredNum = 0;
        Set<String> subjectSet = new HashSet<>();

        System.out.println("이름 입력>");
        String studentName = sc.nextLine();

        next = true;

        while (true) {
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
            int saveStudentId = repository.save(new Student(studentName, subjectSet));
            System.out.println(saveStudentId + "  등록되었습니다.");
            System.out.println("뒤로가기 3, 더 추가할려면 아무키나 입력");
            if (sc.nextLine().equals("3")) break;
        }
    }
}
