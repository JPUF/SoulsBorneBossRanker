package com.example.soulsbornebossranker;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.Math;

public class Contest {

    private DatabaseReference databaseRef;

    private Boss winner;
    private Boss loser;

    public Contest(Boss winner, Boss loser) {
        databaseRef = FirebaseDatabase.getInstance().getReference();
        this.winner = winner;
        this.loser = loser;
        scoreResult(this.winner, this.loser);
    }

    private void scoreResult(Boss winner, Boss loser) {
        Log.i("Result", "winner: " + winner.name + " loser: " + loser.name);

        double qWinner = Math.pow(10,((double) winner.points / 400.0d));
        double qLoser = Math.pow(10,((double) loser.points / 400.0d));
        double expectedWinner = qWinner / (qWinner + qLoser);
        double expectedLoser = qLoser / (qLoser + qWinner);
        winner.points = (int) Math.rint(((double) winner.points) + 32.0d * (1.0d - expectedWinner));
        loser.points  = (int) Math.rint(((double) loser.points)  + 32.0d * (0.0d - expectedLoser));

        Log.i("score", "winner: "+winner.points + " loser: "+loser.points);
        updateScoresInDatabase();

    }

    private void updateScoresInDatabase() {
        DatabaseReference bossRef = databaseRef.child("bosses/" + winner.id);
        bossRef.child("points").setValue(winner.points);
        bossRef = databaseRef.child("bosses/" + loser.id);
        bossRef.child("points").setValue(loser.points);
    }
}
