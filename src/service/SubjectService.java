package service;

import model.Subject;
import repository.Repository;
import repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    public void printSubjectList() {
        List<Subject> list = repository.getList();
        for (Subject subject : list) {
            System.out.print(subject.getId() + ":" + subject.getSubjectName() +  ", ");
        }
        System.out.println();
    }

    /**
     * 과목이름을 과목 아이디로 바꿔서 리스트로 반환
     */
    public ArrayList<Integer> subjectNameAsId(Set<String> findStudentSubjectSet) {
        ArrayList<Integer> subjectIdList = new ArrayList<>();
        List<Subject> list = repository.getList();
        for (Subject subject : list)
            for (String subjectName : findStudentSubjectSet)
                if (subject.getSubjectName().equals(subjectName))
                    subjectIdList.add(subject.getId());
        return subjectIdList;
    }

    public String getName(Integer subjectId) {
        return repository.findById(subjectId).getSubjectName();
    }
}
