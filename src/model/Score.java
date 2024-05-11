package model;

public class Score implements AutoId {
    private static int staticId = 0;
    private int scoreId = 0;
    private int studentId;
    private int step;
    private int score;
    private Grade grade;

    @Override
    public void creatAutoId() {
        scoreId = staticId++;
    }

    @Override
    public int getId() {
        return scoreId;
    }
}
