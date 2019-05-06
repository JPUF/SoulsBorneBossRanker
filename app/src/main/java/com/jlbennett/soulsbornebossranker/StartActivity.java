package com.jlbennett.soulsbornebossranker;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {
    //TODO change implementation so local rankings can be done without internet connection.
    //TODO reduce what's stored online. Only really need to store boss ID and points online I think.
    //TODO on first start, nothing shows for personal rankings. This could be okay, because stuff does show after you vote.
    //TODO reduce minSdkVersion
    //TODO change disclaimer to TextView.
    //TODO taurus demon, Seath, Sanctuary Guardian, stray demon, Vendrick .Just check all the bosses
    //TODO still crashing, null pointer stuff in Contest
    //TODO Memory leaks caused by storing CONTEXT on background threads.

    BottomNavigationView navigation;

    TextView realtimeTextView;
    Button setButton;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = rootRef.child("condition");
    DatabaseReference mBossesRef = rootRef.child("bosses");

    LocalDatabase localDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        localDB = LocalDatabase.getInstance(getApplicationContext());

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(0).setChecked(true);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/OptimusPrincepsSemiBold.ttf");
        TextView about1 = (TextView) findViewById(R.id.about1);
        about1.setTypeface(font);
        TextView about3 = (TextView) findViewById(R.id.about3);
        about3.setTypeface(font);
        TextView about5 = (TextView) findViewById(R.id.about5);
        about5.setTypeface(font);
        TextView about7 = (TextView) findViewById(R.id.about7);
        about7.setTypeface(font);
        TextView about9 = (TextView) findViewById(R.id.about9);
        about9.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "By continuing to use this app, you are agreeing to the <a href='https://github.com/JPUF/SoulsBorneBossRanker/wiki/Privacy-Policy'>Privacy Policy</a>.";
        about9.setText(Html.fromHtml(text));

        realtimeTextView = (TextView) findViewById(R.id.database_tv);
        setButton = (Button) findViewById(R.id.button);
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

                setOnlineDBValues();
            }
        });
        setButton.setVisibility(View.GONE);
        realtimeTextView.setVisibility(View.GONE);
    }

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

    private void setOnlineDBValues() {
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

        bosses.put((id).toString(), new Boss("The Last Giant", id, "ds2", "lastgiant.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Executioner's Chariot", id, "ds2", "chariot.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("The Pursuer", id, "ds2", "pursuer.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Looking Glass Knight", id, "ds2", "lookingglassknight.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("The Skeleton Lords", id, "ds2", "skeletonlords.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Flexile Sentry", id, "ds2", "flexilesentry.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Lost Sinner", id, "ds2", "lostsinner.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Belfry Gargoyles", id, "ds2", "belfrygargoyles.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Ruin Sentinels", id, "ds2", "ruinsentinels.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Royal Rat Vanguard", id, "ds2", "vanguard.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Royal Rat Authority", id, "ds2", "authority.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Scorpioness Najka", id, "ds2", "najka.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("The Duke's Dear Freja", id, "ds2", "freja.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Mytha, the Baneful Queen", id, "ds2", "mytha.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("The Rotten", id, "ds2", "rotten.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Old Dragonslayer", id, "ds2", "dragonslayer.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Covetous Demon", id, "ds2", "covetousdemon.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Smelter Demon", id, "ds2", "smelterdemon.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Old Iron King", id, "ds2", "oldironking.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Guardian Dragon", id, "ds2", "guardiandragon.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Demon of Song", id, "ds2", "demonofsong.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Velstadt, the Royal Aegis", id, "ds2", "velstadt.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Darklurker", id, "ds2", "darklurker.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Dragonrider", id, "ds2", "dragonrider.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Twin Dragonriders", id, "ds2", "twindragonriders.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Prowling Magus and Congregation", id, "ds2", "prowlingmagus.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Dragonrider", id, "ds2", "dragonrider.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Giant Lord", id, "ds2", "giantlord.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Ancient Dragon", id, "ds2", "ancientdragon.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Throne Watcher and Throne Defender", id, "ds2", "watcherdefender.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Nashandra, Queen Regent of Drangleic", id, "ds2", "nashandra.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Aldia, Scholar of the First Sin", id, "ds2", "aldia.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Elana, Squalid Queen", id, "ds2", "elana.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Sinh, the Slumbering Dragon", id, "ds2", "sinh.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Afflicted Graverobber, Ancient Soldier Varg, and Cerah the Old Explorer", id, "ds2", "graverobber.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Blue Smelter Demon", id, "ds2", "bluesmelter.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Fume Knight", id, "ds2", "fumeknight.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Sir Alonne", id, "ds2", "siralonne.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Burnt Ivory King", id, "ds2", "burntivoryking.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Aava, the King's Pet", id, "ds2", "aava.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Lud and Zallen, the King's Pets", id, "ds2", "ludandzallen.jpg", startScore));id++;

        bosses.put((id).toString(), new Boss("Iudex Gundyr", id, "ds3", "iudexgundyr.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Vordt of the Boreal Valley", id, "ds3", "vordt.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Curse-Rotted Greatwood", id, "ds3", "curserotted.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Crystal Sage", id, "ds3", "crystalsage.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Abyss Watchers", id, "ds3", "abysswatchers.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Deacons of the Deep", id, "ds3", "deacons.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("High Lord Wolnir", id, "ds3", "wolnir.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Old Demon King", id, "ds3", "olddemonking.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Pontiff Sulyvahn", id, "ds3", "pontiff.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Yhorm the Giant", id, "ds3", "yhorm.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Aldrich, Devourer of Gods", id, "ds3", "aldrich.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Dancer of the Boreal Valley", id, "ds3", "dancer.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Dragonslayer Armour", id, "ds3", "dragonslayerarmour.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Oceiros, the Consumed King", id, "ds3", "oceiros.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Champion Gundyr", id, "ds3", "championgundyr.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Lothric, Younger Prince and Lorian, Elder Prince", id, "ds3", "twinprinces.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Ancient Wyvern", id, "ds3", "wyvern.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Nameless King", id, "ds3", "namelessking.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Soul of Cinder", id, "ds3", "soulofcinder.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Sister Friede", id, "ds3", "friede.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Champion's Gravetender and Gravetender Greatwolf", id, "ds3", "gravetender.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Demon Prince", id, "ds3", "demonprince.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Halflight, Spear of the Church", id, "ds3", "halflight.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Darkeater Midir", id, "ds3", "midir.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Slave Knight Gael", id, "ds3", "gael.jpg", startScore));id++;

        bosses.put((id).toString(), new Boss("Cleric Beast", id, "bb", "clericbeast.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Father Gascoigne", id, "bb", "gascoigne.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Blood-starved Beast", id, "bb", "bloodstarved.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("The Witch of Hemwick", id, "bb", "hemwick.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Darkbeast Paarl", id, "bb", "paarl.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Vicar Amelia", id, "bb", "amelia.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Shadow of Yharnam", id, "bb", "shadow.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Martyr Logarius", id, "bb", "logarius.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Amygdala", id, "bb", "amygdala.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Rom, the Vacuous Spider", id, "bb", "rom.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("The One Reborn", id, "bb", "reborn.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Celestial Emissary", id, "bb", "emissary.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Ebrietas, Daughter of the Cosmos", id, "bb", "ebrietas.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Micolash, Host of the Nightmare", id, "bb", "micolash.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Mergo's Wet Nurse", id, "bb", "mergos.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Gehrman, the First Hunter", id, "bb", "gehrman.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Moon Presence", id, "bb", "moonpresence.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Ludwig, the Holy Blade", id, "bb", "ludwig.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Laurence, the First Vicar", id, "bb", "laurence.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Living Failures", id, "bb", "failures.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Lady Maria of the Astral Clocktower", id, "bb", "maria.jpg", startScore));id++;
        bosses.put((id).toString(), new Boss("Orphan of Kos", id, "bb", "orphan.jpg", startScore));

        mBossesRef.setValue(bosses);
    }

    public void startVote() {
        Intent intent = new Intent(this, VoteActivity.class);
        startActivity(intent);
        finish();
    }

    public void startRanking() {
        Intent intent = new Intent(this, RankingActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        Log.i("ActivityDestroy", "onDestroy - StartActivity");
    }
}