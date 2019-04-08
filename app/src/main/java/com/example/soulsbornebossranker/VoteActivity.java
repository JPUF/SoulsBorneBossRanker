package com.example.soulsbornebossranker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.resources.TextAppearance;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class VoteActivity extends AppCompatActivity {

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();



    BottomNavigationView navigation;
    Button skipButton;

    //Boss #1             Boss #2
    ImageView bossImage1; ImageView bossImage2;
    ImageView gameImage1; ImageView gameImage2;
    TextView nameText1;   TextView nameText2;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_about:
                    startAbout();
                    return true;
                case R.id.navigation_vote:
                    return true;
                case R.id.navigation_rankings:
                    startRanking();
                    return true;
            }
            return false;
        }
    };

    //TODO skip button's functionality is very temporary. Now, read in a random boss from the database, and then set it using Upper/Lower.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true);
        skipButton = findViewById(R.id.skipButton);

        bossImage1 = findViewById(R.id.bossImage1); bossImage2 = findViewById(R.id.bossImage2);
        gameImage1 = findViewById(R.id.gameImage1); gameImage2 = findViewById(R.id.gameImage2);
        nameText1 = findViewById(R.id.nameText1); nameText2 = findViewById(R.id.nameText2);

        setUpperCardToBoss(new Boss("Ceaseless Discharge", 2, "ds2", "ceaselessdischarge.jpg"));
        setLowerCardToBoss(new Boss("Asylum Demon", 1, "ds1", "asylumdemon.jpg"));

        skipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setUpperCardToBoss(new Boss("Dragonslayer Ornstein and Executioner Smough", 6, "ds1", "ons.jpg"));
                setLowerCardToBoss(new Boss("Twin Princes", 7, "ds3", "twinprinces.jpg"));
                getRandomBoss();
            }
        });
    }

    public Boss getRandomBoss() {
        DatabaseReference bossRef = databaseRef.child("bosses/1/name");
        bossRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String n = snapshot.getValue(String.class);
                Log.i("Boss", n);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "loadBoss, cancelled",  databaseError.toException());
            }
        });
        return new Boss("Twin Princes", 7, "ds3", "twinprinces.jpg");
    }

    public void setUpperCardToBoss(Boss boss) {
        nameText1.setText(boss.name);
        if(boss.game.contains("ds1"))
            gameImage1.setImageResource(R.drawable.ds1);
        else if (boss.game.contains("ds2"))
            gameImage1.setImageResource(R.drawable.ds2);
        else if (boss.game.contains("ds3"))
            gameImage1.setImageResource(R.drawable.ds3);
        else if (boss.game.contains("bb"))
            gameImage1.setImageResource(R.drawable.bb);

        storageRef.child("boss_images/" + boss.imagePath).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(bossImage1);
            }
        });
    }

    public void setLowerCardToBoss(Boss boss) {
        nameText2.setText(boss.name);
        if(boss.game.contains("ds1"))
            gameImage2.setImageResource(R.drawable.ds1);
        else if (boss.game.contains("ds2"))
            gameImage2.setImageResource(R.drawable.ds2);
        else if (boss.game.contains("ds3"))
            gameImage2.setImageResource(R.drawable.ds3);
        else if (boss.game.contains("bb"))
            gameImage2.setImageResource(R.drawable.bb);

        storageRef.child("boss_images/" + boss.imagePath).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(bossImage2);
            }
        });
    }

    public void startAbout() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
    public void startRanking() {
        Intent intent = new Intent(this, RankingActivity.class);
        startActivity(intent);
    }
}
