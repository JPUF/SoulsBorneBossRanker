package com.jlbennett.soulsbornebossranker;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.Executors;

@Database(entities = {Boss.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase INSTANCE;

    public abstract BossDao bossDao();

    public synchronized static LocalDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            Log.i("startupDB", "db didn't exist, building db.");
            INSTANCE = buildDatabase(context);
        }
        Log.i("startupDB", "returning db");
        return INSTANCE;
    }

    private static LocalDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context, LocalDatabase.class, "local-database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).bossDao().insertAll(Boss.populateBosses());
                            }
                        });
                    }
                }).build();
    }
}
