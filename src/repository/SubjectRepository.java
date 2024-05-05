package repository;

import model.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectRepository implements Repository<Subject> {

    private static final List<Subject> subjectList = new ArrayList<>();

    @Override
    public Subject findById(int id) {
        return subjectList.get(id);
    }

    @Override
    public int save(Subject subject) {
        subjectList.add(subject);
        subject.create();
        return subject.getId();
    }

    @Override
    public List<Subject> getList() {
        return subjectList;
    }
}
