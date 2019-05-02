package com.jlbennett.soulsbornebossranker;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingController {

    private WeakReference<RankingActivity> activityRef;//TODO storing this reference is dodgy, quite possibly causing leaks.
    private DatabaseReference databaseRef;
    private ArrayList<Boss> bosses = new ArrayList<>();

    public RankingController(RankingActivity activity) {
        this.activityRef = new WeakReference<RankingActivity>(activity);
        databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    public interface DataStatus{
        void DataIsLoaded(ArrayList<Boss> bosses);
    }

    public void readAllBossesFromFirebase(final RankingController.DataStatus dataStatus) {
        DatabaseReference bossRef = databaseRef.child("bosses");

        bossRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bosses.clear();
                for(DataSnapshot bossSnapshot : dataSnapshot.getChildren()) {
                    bosses.add(bossSnapshot.getValue(Boss.class));
                }
                Collections.sort(bosses, new Comparator<Boss>() {
                    @Override
                    public int compare(Boss b1, Boss b2) {
                        return b2.points - b1.points;
                    }
                });
                dataStatus.DataIsLoaded(bosses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "loadBosses, cancelled", databaseError.toException());
            }
        });
    }

    private static class ReadLocalTask extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... params) {
            final LocalDatabase localDB = (LocalDatabase) params[0];
            final WeakReference<RankingActivity> activityRef = (WeakReference<RankingActivity>) params[1];

            List<Boss> bosses = localDB.bossDao().getAll();//on background thread.
            Collections.sort(bosses, new Comparator<Boss>() {
                @Override
                public int compare(Boss b1, Boss b2) {
                    return b2.points - b1.points;
                }
            });
            final List<Boss> sortedBosses = bosses;

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    activityRef.get().populateTableFromList(sortedBosses);//on UI thread.
                }
            });

            return null;
        }
    }

    public void readAllBossesFromLocal(final Context context) {
        final LocalDatabase localDB = LocalDatabase.getInstance(context);
        new ReadLocalTask().execute(localDB, activityRef);
    }
}
