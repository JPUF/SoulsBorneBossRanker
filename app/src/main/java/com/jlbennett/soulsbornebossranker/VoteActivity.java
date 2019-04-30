package com.jlbennett.soulsbornebossranker;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class VoteActivity extends AppCompatActivity {

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    VoteController voteController;
    BottomNavigationView navigation;
    Button skipButton;

    //Boss #1             Boss #2
    Boss upperBoss;       Boss lowerBoss;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        voteController = new VoteController();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true);
        skipButton = findViewById(R.id.skipButton);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/OptimusPrincepsSemiBold.ttf");

        bossImage1 = findViewById(R.id.bossImage1); bossImage2 = findViewById(R.id.bossImage2);
        gameImage1 = findViewById(R.id.gameImage1); gameImage2 = findViewById(R.id.gameImage2);
        nameText1 = findViewById(R.id.nameText1); nameText2 = findViewById(R.id.nameText2);
        nameText1.setTypeface(font); nameText2.setTypeface(font); skipButton.setTypeface(font);

        setCardsToRandomBosses();

        skipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setCardsToRandomBosses();
            }
        });

        bossImage1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(upperBoss != null && lowerBoss != null) {
                    new Contest(getApplicationContext(), upperBoss, lowerBoss);
                }
                setCardsToRandomBosses();
            }
        });

        bossImage2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lowerBoss != null && upperBoss != null) {
                    new Contest(getApplicationContext(), lowerBoss, upperBoss);
                }
                setCardsToRandomBosses();
            }
        });
    }

    public void setUpperBoss(Boss boss) {
        upperBoss = boss;
        nameText1.setText(boss.name);
        if(boss.game.contains("ds1"))
            gameImage1.setImageResource(R.drawable.ds1);
        else if (boss.game.contains("ds2"))
            gameImage1.setImageResource(R.drawable.ds2);
        else if (boss.game.contains("ds3"))
            gameImage1.setImageResource(R.drawable.ds3);
        else if (boss.game.contains("bb"))
            gameImage1.setImageResource(R.drawable.bb);

        try {
            String pathString = boss.imagePath.substring(0, boss.imagePath.length()-4);
            Uri uri = Uri.parse("android.resource://com.jlbennett.soulsbornebossranker/drawable/" + pathString);
            bossImage1.setImageURI(uri);
        }
        catch (Exception e) {
            Log.e("ImageView", e.toString());
        }
    }

    public void setLowerBoss(Boss boss) {
        lowerBoss = boss;
        nameText2.setText(boss.name);
        if(boss.game.contains("ds1"))
            gameImage2.setImageResource(R.drawable.ds1);
        else if (boss.game.contains("ds2"))
            gameImage2.setImageResource(R.drawable.ds2);
        else if (boss.game.contains("ds3"))
            gameImage2.setImageResource(R.drawable.ds3);
        else if (boss.game.contains("bb"))
            gameImage2.setImageResource(R.drawable.bb);

        try {
            String pathString = boss.imagePath.substring(0, boss.imagePath.length()-4);
            Uri uri = Uri.parse("android.resource://com.jlbennett.soulsbornebossranker/drawable/" + pathString);
            bossImage2.setImageURI(uri);
        }
        catch (Exception e) {
            Log.e("ImageView", e.toString());
        }
    }

    private void setCardsToRandomBosses() {
        voteController.readRandomBoss(new VoteController.DataStatus() {
            @Override
            public void DataIsLoaded(Boss boss) {
                setUpperBoss(boss);
            }
        });

        voteController.readRandomBoss(new VoteController.DataStatus() {
            @Override
            public void DataIsLoaded(Boss boss) {
                setLowerBoss(boss);
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
