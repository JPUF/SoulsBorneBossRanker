package com.example.soulsbornebossranker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RankingActivity extends AppCompatActivity {

    BottomNavigationView navigation;
    LinearLayout ranking_layout;

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

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);

        ranking_layout = (LinearLayout) findViewById(R.id.ranking_layout);
        ranking_layout.addView(createRowCard("ds1", R.string.boss_name, R.string.elo_value));
        ranking_layout.addView(createRowCard("ds3", R.string.boss_name2, R.string.elo_value));

    }

    public void startMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startAbout() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public CardView createRowCard(String game, int name_string_id, int elo_string_id) {
        Context context = getApplicationContext();

        CardView cv = new CardView(context);
        LinearLayout.LayoutParams cvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cv.setLayoutParams(cvParams);
        cv.setCardBackgroundColor(Color.BLACK);


        LinearLayout ll = new LinearLayout(context);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(llParams);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        ImageView iv = new ImageView(context);
        LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.12f);
        iv.setLayoutParams(ivParams);
        iv.setAdjustViewBounds(true);
        if(game.contains("ds1"))
            iv.setImageResource(R.drawable.ds1);
        else if(game.contains("ds2"))
            iv.setImageResource(R.drawable.ds2);
        else if(game.contains("ds3"))
            iv.setImageResource(R.drawable.ds3);
        else if(game.contains("bb"))
            iv.setImageResource(R.drawable.bb);

        TextView name_tv = new TextView(context);
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.85f);
        name_tv.setLayoutParams(nameParams);
        name_tv.setText(name_string_id);
        name_tv.setTextColor(Color.WHITE);

        TextView elo_tv = new TextView(context);
        LinearLayout.LayoutParams eloParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.15f);
        elo_tv.setLayoutParams(eloParams);
        elo_tv.setText(elo_string_id);
        elo_tv.setTextColor(Color.WHITE);
        elo_tv.setTextSize(18);

        ll.addView(iv);
        ll.addView(name_tv);
        ll.addView(elo_tv);
        cv.addView(ll);

        return cv;
    }
}
