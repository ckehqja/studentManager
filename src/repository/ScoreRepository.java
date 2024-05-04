package repository;

import model.Score;

import java.util.ArrayList;
import java.util.List;

public class ScoreRepository implements Repository<Score> {

    private final List<Score> scoreList = new ArrayList<>();

    @Override
    public Score findById(int id) {
        return scoreList.get(id);
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
