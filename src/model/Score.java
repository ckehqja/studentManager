package model;

public class Score implements AutoId{
    static private int scoreId = 0;
    private int studentId;
    private int step;
    private int score;
    private Grade grade;

    @Override
    public void creatAutoId() {
        scoreId++;
    }

    @Override
    public int getId() {
        return scoreId;
    }
}
