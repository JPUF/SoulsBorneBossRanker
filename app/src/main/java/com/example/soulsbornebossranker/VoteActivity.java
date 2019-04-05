package com.example.soulsbornebossranker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.resources.TextAppearance;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VoteActivity extends AppCompatActivity {

    BottomNavigationView navigation;
    LinearLayout card_layout;

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
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true);

        card_layout = (LinearLayout) findViewById(R.id.card_layout);
        card_layout.addView(createBossCard("Dragonslayer Ornstein and Executioner Smough", R.drawable.ons, R.drawable.ds1));
        card_layout.addView(createBossCard("Centipede Demon", R.drawable.centipededemon, R.drawable.ds1));
    }

    private CardView createBossCard(String bossName, int bossImgID, int gameImgID) {
        Context context = getApplicationContext();

        CardView cv = new CardView(context);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
        cardParams.setMarginStart(64);
        cardParams.setMarginEnd(64);
        cardParams.setMargins(0,16,0,0);
        cv.setLayoutParams(cardParams);
        cv.setCardElevation(10);
        cv.setBackgroundColor(Color.BLACK);

        LinearLayout outer_ll = new LinearLayout(context);
        LinearLayout.LayoutParams outerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        outer_ll.setOrientation(LinearLayout.VERTICAL);
        outer_ll.setLayoutParams(outerParams);

        ImageView boss_image = new ImageView(context);
        LinearLayout.LayoutParams bossImgParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.75f);
        boss_image.setLayoutParams(bossImgParams);
        boss_image.setCropToPadding(false);
        boss_image.setImageResource(bossImgID);

        LinearLayout inner_ll = new LinearLayout(context);
        LinearLayout.LayoutParams innerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.17f);
        inner_ll.setOrientation(LinearLayout.HORIZONTAL);
        inner_ll.setLayoutParams(innerParams);
        inner_ll.setBackgroundColor(Color.BLACK);

        ImageView game_image = new ImageView(context);
        LinearLayout.LayoutParams gameImgParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.15f);
        game_image.setLayoutParams(gameImgParams);
        game_image.setAdjustViewBounds(true);
        game_image.setImageResource(gameImgID);

        TextView name_tv = new TextView(context);
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        name_tv.setLayoutParams(nameParams);
        name_tv.setMaxLines(2);
        name_tv.setPadding(5,0,0,0);
        name_tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        name_tv.setTextColor(Color.WHITE);
        name_tv.setTypeface(Typeface.DEFAULT_BOLD);
        name_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//TODO text size not scaling properly. Currently optimised for 720p.
        name_tv.setText(bossName);

        inner_ll.addView(game_image);
        inner_ll.addView(name_tv);
        outer_ll.addView(boss_image);
        outer_ll.addView(inner_ll);
        cv.addView(outer_ll);

        return cv;
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
