package com.example.soulsbornebossranker;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "games")
public class Game {
    @PrimaryKey
    public int id;

    public boolean played;

    public Game(int id) {
        this.id = id;
        this.played = true;
    }
}
