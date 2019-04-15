package com.example.soulsbornebossranker;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "bosses")
public class Boss {
    @PrimaryKey
    public int id;

    public String name;
    public String game;
    public String imagePath;
    public int points;

    @Ignore
    public Boss() {}

    public Boss(String name, int id, String game, String imagePath, int points) {
        this.name = name;
        this.id = id;
        this.game = game;
        this.imagePath = imagePath;
        this.points = points;
    }
}
