package repository;

import model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository implements Repository<Student> {
    private static final List<Student> studentList = new ArrayList<>();

    @Override
    public Student findById(int id) {
        return studentList.get(id);
    }

    @Override
    public int save(Student student) {
        studentList.add(student);
        student.create();
        return student.getId();
    }

    @Override
    public boolean delete(int id) {
        return studentList.remove(id) != null;
    }

    @Override
    public List<Student> getList() {
        return studentList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }
}
