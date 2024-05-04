package repository;

import model.Score;

import java.util.ArrayList;
import java.util.List;

public class ScoreRepository implements Repository<Score> {

    private static List<Score> scoreList = new ArrayList<>();

    @Override
    public Score findById(int id) {
        return scoreList.get(id);
    }

    public Score findBy2Id(int studentId, int subjectId) {
        for (Score score : scoreList) {
            if(score.getStudentId() == studentId && score.getSubjectId() == subjectId) {
                return score;
            }
        }
        return null;
    }

    @Override
    public int save(Score score) {
        scoreList.add(score);
        score.create();
        return score.getId();
    }

    @Override
    public List<Score> getList() {
        return scoreList;
    }
}
