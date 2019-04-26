package com.example.soulsbornebossranker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {
    //TODO views in a Recycler instead?

    private enum Ranking {
        GLOBAL, PERSONAL
    }

    RankingController rankingController;

    BottomNavigationView navigation;
    Button personalButton;
    Button globalButton;
    ImageView arrow;
    LinearLayout ranking_layout;
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

        font = Typeface.createFromAsset(getAssets(),"fonts/OptimusPrincepsSemiBold.ttf");

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);

        personalButton = (Button) findViewById(R.id.personalButton);
        globalButton = (Button) findViewById(R.id.globalButton);
        arrow = (ImageView) findViewById(R.id.arrow);
        ranking_layout = (LinearLayout) findViewById(R.id.ranking_layout);

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
        ranking_layout.removeAllViews();
        rankingController.readAllBossesFromFirebase(new RankingController.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Boss> bosses) {
                int rank = 1;
                for(Boss boss : bosses) {
                    ranking_layout.addView(createRow(boss, rank++));
                }
            }
        });
    }

    private void populateTableFromLocal() {
        ranking_layout.removeAllViews();
        rankingController.readAllBossesFromLocal(getApplicationContext());
    }

    public void populateTableFromList(List<Boss> bosses) {
        ranking_layout.removeAllViews();
        int rank = 1;
        for(Boss boss : bosses) {
            ranking_layout.addView(createRow(boss, rank++));
        }
    }

    public LinearLayout createRow(Boss boss, Integer rank) {
        Context context = getApplicationContext();

        LinearLayout outer_ll = new LinearLayout(context);
        LinearLayout.LayoutParams outerLLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        outerLLParams.setMargins(0, 0,0,10);
        outer_ll.setLayoutParams(outerLLParams);
        outer_ll.setBackgroundColor(Color.BLACK);

        TextView rank_tv = new TextView(context);
        LinearLayout.LayoutParams rankParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.11f);
        rank_tv.setLayoutParams(rankParams);
        rank_tv.setText(rank.toString());
        rank_tv.setTextColor(Color.WHITE);
        rank_tv.setTextSize(18);
        rank_tv.setTextAppearance(Typeface.BOLD);
        rank_tv.setPadding(8,0,0,0);
        rank_tv.setGravity(Gravity.CENTER);
        rank_tv.setGravity(Gravity.CENTER_VERTICAL);

        CardView cv = new CardView(context);
        LinearLayout.LayoutParams cvParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.95f);
        cv.setLayoutParams(cvParams);
        cv.setCardBackgroundColor(Color.BLACK);


        LinearLayout ll = new LinearLayout(context);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(llParams);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        ImageView iv = new ImageView(context);
        LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.11f);
        iv.setLayoutParams(ivParams);
        iv.setAdjustViewBounds(true);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        if(boss.game.contains("ds1"))
            iv.setImageResource(R.drawable.ds1);
        else if(boss.game.contains("ds2"))
            iv.setImageResource(R.drawable.ds2);
        else if(boss.game.contains("ds3"))
            iv.setImageResource(R.drawable.ds3);
        else if(boss.game.contains("bb"))
            iv.setImageResource(R.drawable.bb);

        TextView name_tv = new TextView(context);
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.85f);
        nameParams.setMargins(8,0,2,0);
        name_tv.setLayoutParams(nameParams);
        name_tv.setGravity(Gravity.CENTER_VERTICAL);
        name_tv.setTypeface(font);
        name_tv.setText(boss.name);
        name_tv.setTextColor(Color.WHITE);
        name_tv.setTextSize(12);
        name_tv.setMaxLines(2);

        TextView elo_tv = new TextView(context);
        LinearLayout.LayoutParams eloParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.15f);
        elo_tv.setLayoutParams(eloParams);
        Integer points = boss.points;
        elo_tv.setText(points.toString());
        elo_tv.setTextColor(Color.WHITE);
        elo_tv.setTextSize(18);
        elo_tv.setGravity(Gravity.CENTER);
        elo_tv.setGravity(Gravity.CENTER_VERTICAL);

        ll.addView(iv);
        ll.addView(name_tv);
        ll.addView(elo_tv);
        cv.addView(ll);

        outer_ll.addView(rank_tv);
        outer_ll.addView(cv);

        return outer_ll;
    }

    public void startMain() {
        Intent intent = new Intent(this, VoteActivity.class);
        startActivity(intent);
    }

    public void startAbout() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
