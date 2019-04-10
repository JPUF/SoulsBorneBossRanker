package com.example.soulsbornebossranker;

import android.util.Log;
import java.lang.Math;

public class Contest {

    //TODO demo writing score to DB. Then start figuring out ELO stuff.

    public static void scoreResult(Boss winner, Boss loser) {
        Log.i("Result", "winner: " + winner.name + " loser: " + loser.name);
        int winnerPoints = winner.points;
        int loserPoints = loser.points;
        winnerPoints = (int) Math.pow(10,winnerPoints/400);
        loserPoints = (int) Math.pow(10,loserPoints/400);
        int winnerExpected = winnerPoints / (winnerPoints + loserPoints);
        int loserExpected = loserPoints / (loserPoints + winnerPoints);

        winner.points = winnerPoints + 32 * (1 - winnerExpected);
        loser.points = loserPoints + 32 * (0 - loserExpected);

        Log.i("score", "winner: "+winner.points + " loser: "+loser.points);

    }
}
