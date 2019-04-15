package com.example.soulsbornebossranker;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Boss.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract BossDao bossDao();
}
