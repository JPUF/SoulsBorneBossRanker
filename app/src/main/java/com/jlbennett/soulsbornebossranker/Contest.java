package com.jlbennett.soulsbornebossranker;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.Math;
import java.lang.ref.WeakReference;

public class Contest {

    private DatabaseReference databaseRef;
    //private LocalDatabase localDB;
    private final WeakReference<LocalDatabase> localDB;

    private Boss winnerOnline;
    private Boss loserOnline;

    public Contest(Context context, Boss winner, Boss loser) {
        databaseRef = FirebaseDatabase.getInstance().getReference();
        //localDB = LocalDatabase.getInstance(context);
        this.localDB = new WeakReference<>(LocalDatabase.getInstance(context));
        this.winnerOnline = winner;
        this.loserOnline = loser;
        scoreResultOnline();
        scoreResultLocal();
    }

    private void scoreResultOnline() {
        int newPoints[] = eloScore(winnerOnline.points, loserOnline.points);
        winnerOnline.points = newPoints[0];
        loserOnline.points  = newPoints[1];
        DatabaseReference bossRef = databaseRef.child("bosses/" + winnerOnline.id);
        bossRef.child("points").setValue(winnerOnline.points);
        bossRef = databaseRef.child("bosses/" + loserOnline.id);
        bossRef.child("points").setValue(loserOnline.points);
    }

    private void scoreResultLocal() {
        Log.i("startupDB", localDB.toString());
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {//on background thread.
                try {
                    Boss winnerLocal = localDB.get().bossDao().loadBoss(winnerOnline.id);
                    Boss loserLocal = localDB.get().bossDao().loadBoss(loserOnline.id);
                    int newPoints[] = eloScore(winnerLocal.points, winnerLocal.points);
                    localDB.get().bossDao().updateBoss(winnerLocal.id, newPoints[0]);
                    localDB.get().bossDao().updateBoss(loserLocal.id, newPoints[1]);
                }
                catch (NullPointerException e) {
                    Log.e("Contest", "error reading from local database");
                }
            }
        });
    }

    private int[] eloScore(int winnerPoints, int loserPoints) {
        double qWinner = Math.pow(10,((double) winnerPoints / 400.0d));
        double qLoser = Math.pow(10,((double) loserPoints / 400.0d));
        double expectedWinner = qWinner / (qWinner + qLoser);
        double expectedLoser = qLoser / (qLoser + qWinner);
        winnerPoints = (int) Math.rint(((double) winnerPoints) + 32.0d * (1.0d - expectedWinner));
        loserPoints  = (int) Math.rint(((double) loserPoints)  + 32.0d * (0.0d - expectedLoser));
        int returnArray[] = {winnerPoints, loserPoints};
        return returnArray;
    }
}
