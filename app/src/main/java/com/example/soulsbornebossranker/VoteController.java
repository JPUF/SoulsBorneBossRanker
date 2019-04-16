package com.example.soulsbornebossranker;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class VoteController {
    private Boss boss;
    private DatabaseReference databaseRef;
    private boolean playedGames[];
    private static int bossInUse = 0;

    public VoteController(boolean playedGames[]) {
        this.playedGames = playedGames;
        databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    public interface DataStatus{
        void DataIsLoaded(Boss upperBoss);
    }


    //TODO pick random boss value, then filter out if it shouldn't be picked.
    public void readRandomBoss(final DataStatus dataStatus) {
        int randomBossID;
        int randomDS1; int randomDS2; int randomDS3; int randomBB;
        ArrayList<Integer> ranges = new ArrayList<>();
        if(playedGames[0]){ranges.add(0);ranges.add(22);}
        if(playedGames[1]){ranges.add(22);ranges.add(63);}
        if(playedGames[2]){ranges.add(63);ranges.add(88);}
        if(playedGames[3]){ranges.add(88);ranges.add(110);}
        Log.i("randomWI", "ds1 = " + playedGames[0] + " ds2 = " + playedGames[1] + " ds3 = " + playedGames[2] + " bb = " + playedGames[3]);
        do {
            randomBossID = ThreadLocalRandom.current().nextInt(0, 110) + 1;
        } while(bossInUse == randomBossID ||
                (!playedGames[0] && (randomBossID > 0 && randomBossID <= 22)) ||
                (!playedGames[1] && (randomBossID > 22 && randomBossID <= 63)) ||
                (!playedGames[2] && (randomBossID > 63 && randomBossID <= 88)) ||
                (!playedGames[3] && (randomBossID > 88 && randomBossID <= 110)));
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
