package com.example.soulsbornebossranker;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    BottomNavigationView navigation;
    TextView realtimeTextView;
    Button setButton;
    ImageView testImage;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = rootRef.child("condition");
    DatabaseReference mBossesRef = rootRef.child("bosses");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_about:

                    return true;
                case R.id.navigation_vote:
                    startVote();
                    return true;
                case R.id.navigation_rankings:
                    startRanking();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(0).setChecked(true);

        realtimeTextView = (TextView) findViewById(R.id.database_tv);
        setButton = (Button) findViewById(R.id.button);
        testImage = (ImageView) findViewById(R.id.test_iv);

    }

    public void startVote() {
        Intent intent = new Intent(this, VoteActivity.class);
        startActivity(intent);
    }

    public void startRanking() {
        Intent intent = new Intent(this, RankingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                realtimeTextView.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String dateS = dateFormat.format(date);
                mConditionRef.setValue(dateS);


                Map<String, Boss> bosses = new HashMap<>();
                bosses.put("1", new Boss("Asylum Demon", 1, "ds1", "asylumdemon.jpg"));
                bosses.put("2", new Boss("Bell Gargoyle", 2, "ds1", "bellgargoyle.jpg"));
                bosses.put("3", new Boss("Capra Demon", 3, "ds1", "caprademon.jpg"));
                bosses.put("4", new Boss("Ceaseless Discharge", 4, "ds1", "ceaselessdischarge.jpg"));
                bosses.put("5", new Boss("Centipede Demon", 5, "ds1", "centipededemon.jpg"));
                bosses.put("6", new Boss("Ornstein and Smough", 6, "ds1", "ons.jpg"));
                bosses.put("7", new Boss("Twin Princes", 7, "ds3", "twinprinces.jpg"));
                mBossesRef.setValue(bosses);


                storageRef.child("boss_images/bellgargoyle.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(testImage);
                    }
                });
            }
        });
    }
}
