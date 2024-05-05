package service;

import model.Subject;
import repository.Repository;
import repository.SubjectRepository;

import java.util.List;

public class SubjectService implements Service<Subject> {

    private static final Repository<Subject> repository = new SubjectRepository();

    @Override
    public int save(Subject subject) {
        return repository.save(subject);
    }

    @Override
    public Subject findById(int id) {
        return repository.findById(id);
    }

    public void printSubjectList() {
        List<Subject> list = repository.getList();
        for (Subject subject : list) {
            System.out.print(subject.getId() + ":" + subject.getSubjectName() +  ", ");
        }
        System.out.println();
    }
}
