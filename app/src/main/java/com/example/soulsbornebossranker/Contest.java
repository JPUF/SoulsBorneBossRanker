package com.example.soulsbornebossranker;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.Math;

public class Contest {

    private DatabaseReference databaseRef;

    private Boss winner;
    private Boss loser;

    public Contest(Context context, Boss winner, Boss loser) {
        databaseRef = FirebaseDatabase.getInstance().getReference();
        this.winner = winner;
        this.loser = loser;
        scoreResult(context, this.winner, this.loser);
    }

    private void scoreResult(Context context, Boss winner, Boss loser) {
        Log.i("Result", "winner: " + winner.name + " loser: " + loser.name);

        double qWinner = Math.pow(10,((double) winner.points / 400.0d));
        double qLoser = Math.pow(10,((double) loser.points / 400.0d));
        double expectedWinner = qWinner / (qWinner + qLoser);
        double expectedLoser = qLoser / (qLoser + qWinner);
        winner.points = (int) Math.rint(((double) winner.points) + 32.0d * (1.0d - expectedWinner));
        loser.points  = (int) Math.rint(((double) loser.points)  + 32.0d * (0.0d - expectedLoser));

        Log.i("score", "winner: "+winner.points + " loser: "+loser.points);
        updateScoresInFirebase();
        updateScoresInLocal(context);

    }

    private void updateScoresInFirebase() {
        DatabaseReference bossRef = databaseRef.child("bosses/" + winner.id);
        bossRef.child("points").setValue(winner.points);
        bossRef = databaseRef.child("bosses/" + loser.id);
        bossRef.child("points").setValue(loser.points);
    }

    private void updateScoresInLocal(Context context) {
        final LocalDatabase localDB = Room.databaseBuilder(context, LocalDatabase.class, "local-database").build();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {//on background thread.
                localDB.bossDao().updateBoss(winner.id, winner.points);
                localDB.bossDao().updateBoss(loser.id, loser.points);
                localDB.close();
            }
        });
    }
}
