package service;

import model.Student;
import repository.Repository;
import repository.StudentRepository;

import java.util.List;

public class StudentService implements Service<Student> {

    private static final Repository<Student> repository = new StudentRepository();

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
}
