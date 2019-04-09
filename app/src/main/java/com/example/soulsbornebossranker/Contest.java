package com.example.soulsbornebossranker;

import android.util.Log;

public class Contest {

    //TODO demo writing score to DB. Then start figuring out ELO stuff.

    public static void scoreResult(Boss winner, Boss loser) {
        Log.i("Result", "winner: " + winner.name + " loser: " + loser.name);
    }
}
