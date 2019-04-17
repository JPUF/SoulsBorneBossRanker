package com.example.soulsbornebossranker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface GameDao {
    @Query("SELECT * FROM games WHERE id = :gameID")
    Game loadGame(int gameID);

    @Query("UPDATE games SET played = :played WHERE id = :gameID")
    void updateBoss(int gameID, boolean played);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Game> games);
}
