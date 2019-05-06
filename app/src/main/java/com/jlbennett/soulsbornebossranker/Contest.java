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

    private static class LocalStorageTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected Void doInBackground(Object... params) {//on background thread.
            try {
                LocalDatabase localDB = (LocalDatabase) params[0];
                Integer winnerID = (Integer) params[1];
                Integer loserID = (Integer) params[2];
                Boss winnerLocal = localDB.bossDao().loadBoss(winnerID);
                Boss loserLocal = localDB.bossDao().loadBoss(loserID);
                int newPoints[] = eloScore(winnerLocal.points, winnerLocal.points);
                localDB.bossDao().updateBoss(winnerLocal.id, newPoints[0]);
                localDB.bossDao().updateBoss(loserLocal.id, newPoints[1]);
            }
            catch (NullPointerException e) {
                Log.e("Contest", "error reading from local database");
            }
            return null;
        }
    }

    private void scoreResultLocal() {
        LocalDatabase localDatabaseRef = localDB.get();//could try getting as a weak reference, pass that to Task.
        new LocalStorageTask().execute(localDatabaseRef, winnerOnline.id, loserOnline.id);//probs try catch.
    }

    private static int[] eloScore(int winnerPoints, int loserPoints) {
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
