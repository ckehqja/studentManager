package service;

import model.Score;
import repository.Repository;
import repository.ScoreRepository;

import java.util.ArrayList;

public class ScoreService implements Service<Score> {

    Repository<Score> repository = new ScoreRepository();

    @Override
    public int save(Score score) {
        return repository.save(score);
    }

    @Override
    public Score findById(int id) {
        return repository.findById(id);
    }
}
