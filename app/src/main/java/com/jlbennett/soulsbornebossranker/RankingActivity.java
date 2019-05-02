package com.jlbennett.soulsbornebossranker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    private enum Ranking {
        GLOBAL, PERSONAL
    }

    RankingController rankingController;
    Context context;

    BottomNavigationView navigation;
    Button personalButton;
    Button globalButton;
    ImageView arrow;

    RecyclerView rankingRecycler;
    LinearLayoutManager llm;

    Typeface font;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_about:
                    startAbout();
                    return true;
                case R.id.navigation_vote:
                    startMain();
                    return true;
                case R.id.navigation_rankings:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        rankingController = new RankingController(this);
        context = getApplicationContext();

        font = Typeface.createFromAsset(getAssets(),"fonts/OptimusPrincepsSemiBold.ttf");

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);

        personalButton = (Button) findViewById(R.id.personalButton);
        globalButton = (Button) findViewById(R.id.globalButton);
        arrow = (ImageView) findViewById(R.id.arrow);

        rankingRecycler = (RecyclerView) findViewById(R.id.rankingRecycler);
        rankingRecycler.setHasFixedSize(true);
        llm = new LinearLayoutManager(context);
        rankingRecycler.setLayoutManager(llm);

        /*
        List<Boss> bosses = new ArrayList<>();
        for(int i = 0; i < 110; i++){
            bosses.add(new Boss("Boss 1", 1, "ds1", "bedofchaos.jpg", 1000));
        }


        RankingRecyclerAdapter adapter = new RankingRecyclerAdapter(bosses);
        rankingRecycler.setAdapter(adapter);
        */


        displayRankings(Ranking.GLOBAL);

        personalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                displayRankings(Ranking.PERSONAL);
            }
        });

        globalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                displayRankings(Ranking.GLOBAL);
            }
        });
    }


    private void displayRankings(Ranking ranking) {
        if(ranking == Ranking.GLOBAL){
            populateTableFromFirebase();
            arrow.setRotation(180);
            personalButton.setBackgroundColor(Color.BLACK);
            personalButton.setTextColor(Color.WHITE);
            globalButton.setBackgroundColor(Color.WHITE);
            globalButton.setTextColor(Color.BLACK);
        }
        else if(ranking == Ranking.PERSONAL) {
            populateTableFromLocal();
            arrow.setRotation(0);

            globalButton.setBackgroundColor(Color.BLACK);
            globalButton.setTextColor(Color.WHITE);
            personalButton.setBackgroundColor(Color.WHITE);
            personalButton.setTextColor(Color.BLACK);
        }
    }


    private void populateTableFromFirebase() {
        rankingController.readAllBossesFromFirebase(new RankingController.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Boss> bosses) {
                RankingRecyclerAdapter adapter = new RankingRecyclerAdapter(bosses);
                rankingRecycler.setAdapter(adapter);
            }
        });
    }

    private void populateTableFromLocal() {
        rankingController.readAllBossesFromLocal(context);
    }

    public void populateTableFromList(List<Boss> bosses) {
        RankingRecyclerAdapter adapter = new RankingRecyclerAdapter(bosses);
        rankingRecycler.setAdapter(adapter);
    }

    public void startMain() {
        Intent intent = new Intent(this, VoteActivity.class);
        startActivity(intent);
        finish();
    }

    public void startAbout() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        Log.i("ActivityDestroy", "onDestroy - RankingActivity");
    }
}
