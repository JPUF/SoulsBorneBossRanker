package com.jlbennett.soulsbornebossranker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BossDao {
    @Query("SELECT * FROM bosses")
    List<Boss> getAll();

    @Query("SELECT * FROM bosses WHERE id = :bossID")
    Boss loadBoss(int bossID);

    @Query("UPDATE bosses SET points = :newPoints WHERE id = :bossID")
    void updateBoss(int bossID, int newPoints);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Boss> bosses);
}
