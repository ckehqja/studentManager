package service;

import model.Subject;
import repository.Repository;
import repository.SubjectRepository;

public class SubjectService implements Service<Subject> {

    private final Repository<Subject> repository = new SubjectRepository();

    @Override
    public int save(Subject subject) {
        return repository.save(subject);
    }

    @Override
    public Subject findById(int id) {
        return repository.findById(id);
    }


}
