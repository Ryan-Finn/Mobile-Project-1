package edu.sdsmt.project1.Model;

public class Player {
    private final String name;
    private final int id;
    private int score = 0;

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void incScore(int add) {
        this.score += add;
    }
}
