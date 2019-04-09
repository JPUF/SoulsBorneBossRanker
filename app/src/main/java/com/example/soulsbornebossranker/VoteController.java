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

import java.util.concurrent.ThreadLocalRandom;

public class VoteController {
    private Boss boss;
    private StorageReference storageRef;
    private DatabaseReference databaseRef;

    public VoteController() {
        storageRef = FirebaseStorage.getInstance().getReference();
        databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    public interface DataStatus{
        void DataIsLoaded(Boss upperBoss);
    }


    public void readRandomBoss(final DataStatus dataStatus) {
        int randomBossID = ThreadLocalRandom.current().nextInt(1, 11 + 1);
        DatabaseReference bossRef = databaseRef.child("bosses/" + randomBossID);

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
