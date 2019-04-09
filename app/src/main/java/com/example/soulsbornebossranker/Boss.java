package com.example.soulsbornebossranker;

public class Boss {
    public String name;
    public int id;
    public String game;
    public String imagePath;
    public int points;

    public Boss() {}
    public Boss(String name, int id, String game, String imagePath, int points) {
        this.name = name;
        this.id = id;
        this.game = game;
        this.imagePath = imagePath;
        this.points = points;
    }
}
