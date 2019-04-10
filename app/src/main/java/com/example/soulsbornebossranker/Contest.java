package com.example.soulsbornebossranker;

import android.util.Log;
import java.lang.Math;

public class Contest {

    //TODO demo writing score to DB. Then start figuring out ELO stuff.

    public static void scoreResult(Boss winner, Boss loser) {
        Log.i("Result", "winner: " + winner.name + " loser: " + loser.name);

        double qWinner = Math.pow(10,((double) winner.points / 400.0d));
        double qLoser = Math.pow(10,((double) loser.points / 400.0d));
        double expectedWinner = qWinner / (qWinner + qLoser);
        double expectedLoser = qLoser / (qLoser + qWinner);
        winner.points = winner.points + 32 * (1 - (int) expectedWinner);
        loser.points = loser.points + 32 * (0 - (int) expectedLoser);

        Log.i("score", "winner: "+winner.points + " loser: "+loser.points);

    }
}
