package service;

import model.Score;
import model.Student;
import model.StudentStatus;
import repository.Repository;
import repository.ScoreRepository;
import repository.StudentRepository;

import java.util.List;

public class StudentService implements Service<Student> {

    StudentStatus studentStatus;
    private final Repository<Student> repository = new StudentRepository();
    private final Repository<Score> scoreRepository = new ScoreRepository();

    @Override
    public int save(Student student) {
        return repository.save(student);
    }

    @Override
    public Student findById(int id) {
        return repository.findById(id);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
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

    public void printStudent(int studentId) {
        Student findStudent = repository.findById(studentId);
        String status;
        try {
            status = findStudent.getStudentStatus().getValue();
        } catch (NullPointerException e) {
            status = "null";
        }
        System.out.printf("%-10s %-10s %-10s %-10s\n", "아이디", "이름", "상태", "과목");
        System.out.printf("%-11d %-10s %-11s %-10s\n",
                studentId, findStudent.getStudentName(), status, findStudent.getSubjectSet());
    }

    public void setStudentStatus(int id, String status) {
        Student findStudent = repository.findById(id);
        studentStatus = switch (status) {
            case "1" -> StudentStatus.GREEN;
            case "2" -> StudentStatus.RED;
            case "3" -> StudentStatus.YELLOW;
            default -> null;
        };
        findStudent.setStudentStatus(studentStatus);
    }

    /**
     * 상태를 입력받아 상태 변경
     */
    public void printStudentByStatus(String status) {
        List<Student> list = repository.getList();
        studentStatus = switch (status) {
            case "1" -> StudentStatus.GREEN;
            case "2" -> StudentStatus.RED;
            case "3" -> StudentStatus.YELLOW;
            default -> null;
        };
        System.out.println("id - 이름 -  상태");
        list.stream()
                .filter(student -> student.getStudentStatus().getValue().equals(studentStatus.getValue()))
                .forEach(student -> System.out.println(
                        student.getStudentId() + " - " + student.getStudentName()
                                + " - " + student.getStudentStatus().getValue()));
        System.out.println();
    }
}
