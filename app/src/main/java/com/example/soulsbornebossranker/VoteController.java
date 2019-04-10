package com.example.soulsbornebossranker;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class VoteController {
    private Boss boss;
    private DatabaseReference databaseRef;
    private static int bossInUse = 0;

    public VoteController() {
        databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    public interface DataStatus{
        void DataIsLoaded(Boss upperBoss);
    }


    public void readRandomBoss(final DataStatus dataStatus) {
        int randomBossID;
        do {
            randomBossID = ThreadLocalRandom.current().nextInt(0, 22) + 1;//bound = # of bosses -1
        } while(bossInUse == randomBossID);
        bossInUse = randomBossID;
        DatabaseReference bossRef = databaseRef.child("bosses/" + randomBossID);
        Log.i("ControllerBoss", "randomID: " + randomBossID);

        bossRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boss b = dataSnapshot.getValue(Boss.class);
                Log.i("ControllerBoss", "inner: " + b.name);
                boss = b;
                dataStatus.DataIsLoaded(boss);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "loadBoss, cancelled", databaseError.toException());
            }
        });
    }

}
