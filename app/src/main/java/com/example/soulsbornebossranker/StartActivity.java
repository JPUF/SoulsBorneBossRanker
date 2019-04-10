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

                storageRef.child("boss_images/bellgargoyle.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(testImage);
                    }
                });

                int startScore = 1000;
                Integer id = 1;
                Map<String, Boss> bosses = new HashMap<>();
                bosses.put((id).toString(), new Boss("Asylum Demon", id, "ds1", "asylumdemon.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Bed of Chaos", id, "ds1", "bedofchaos.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Bell Gargoyles", id, "ds1", "bellgargoyle.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Black Dragon Kalameet", id, "ds1", "kalameet.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Capra Demon", id, "ds1", "caprademon.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Ceaseless Discharge", id, "ds1", "ceaselessdischarge.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Centipede Demon", id, "ds1", "centipededemon.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Chaos Witch Quelaag", id, "ds1", "quelaag.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Crossbreed Priscilla", id, "ds1", "priscilla.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Dark Sun Gwyndolin", id, "ds1", "gwyndolin.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Demon Firesage", id, "ds1", "demonfiresage.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Dragonslayer Ornstein and Executioner Smough", id, "ds1", "ons.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Four Kings", id, "ds1", "fourkings.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Gaping Dragon", id, "ds1", "gapingdragon.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Gravelord Nito", id, "ds1", "nito.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Great Grey Wolf Sif", id, "ds1", "sif.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Gwyn, Lord of Cinder", id, "ds1", "gwyn.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Iron Golem", id, "ds1", "irongolem.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Knight Artorias", id, "ds1", "artorias.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Manus, Father of the Abyss", id, "ds1", "manus.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Moonlight Butterfly", id, "ds1", "moonlightbutterfly.jpg", startScore));id++;
                bosses.put((id).toString(), new Boss("Pinwheel", id, "ds1", "pinwheel.jpg", startScore));id++;

                bosses.put((id).toString(), new Boss("Twin Princes", id, "ds3", "twinprinces.jpg", startScore));id++;
                mBossesRef.setValue(bosses);


            }
        });
    }
}

