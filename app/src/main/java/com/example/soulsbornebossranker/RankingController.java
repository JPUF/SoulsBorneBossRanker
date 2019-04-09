package com.example.soulsbornebossranker;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class RankingController {

    private DatabaseReference databaseRef;
    private ArrayList<Boss> bosses = new ArrayList<>();

    public RankingController() {
        databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    public interface DataStatus{
        void DataIsLoaded(ArrayList<Boss> bosses);
    }

    public void readAllBosses(final RankingController.DataStatus dataStatus) {
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
}
