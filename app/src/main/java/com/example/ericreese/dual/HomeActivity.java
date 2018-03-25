package com.example.ericreese.dual;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ericreese.dual.Matches.MatchesActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


/**
 * Created by ericreese on 3/24/18.
 */

public class HomeActivity extends AppCompatActivity {
    private Button swipe;
    private Button profile;
    private Button currentmatches;
    private Button add_category;
    private Button support;
    private Button signout;
    private EditText category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.category = (EditText) findViewById(R.id.category);
        this.swipe = (Button) this.findViewById(R.id.swipe);
        this.profile = (Button) this.findViewById(R.id.profile);
        this.currentmatches = (Button) this.findViewById(R.id.current_matches);
        this.add_category =  (Button) this.findViewById(R.id.add_category);
        this.signout = (Button) this.findViewById(R.id.sign_out);

        this.swipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent gotoswipe = new Intent(context, SwipeCardsActivity.class);
                startActivity(gotoswipe);
                finish();
            }
        });

        this.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent goToEditProfile = new Intent(context, EditProfileActivity.class);
                startActivity(goToEditProfile );
                finish();
            }
        });

        MainActivity.mReference.addChildEventListener(new ChildEventListener() {

            String value = "";
            boolean begin = false;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.getValue().equals("Terminate")) {
                        break;
                    } else if (MainActivity.keys.contains(child.getKey())) {
                        continue;
                    }

                    if (begin) {
                        MainActivity.keys.add(child.getKey());
                    } else if (child.getValue().equals("Begin")) {
                        begin = true;
                    }
                }
            }

            //Necessary methods to implement interface
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        //Matches
        this.currentmatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent gotomatches = new Intent(context, MatchesActivity.class);
                startActivity(gotomatches);
                finish();
            }
        });

        /*
        this.support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent getsupport = new Intent(context, EditProfileActivity.class);
                startActivity(getsupport);
                finish();
            }
        });
        */
        this.add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent goToAddCategory = new Intent(context, AddCategory.class);
                startActivity(goToAddCategory);
                finish();
            }
        });
        this.signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.loggedIn = false;
                Context context = v.getContext();
                MainActivity.loggedIn = false;
                Intent signout = new Intent(context, MainActivity.class);
                startActivity(signout);
                finish();
            }
        });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //fetches different subreddits
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        for (int i = 0; i < MainActivity.keys.size(); i++) {
            menu.add(0, menu.size(), Menu.NONE, MainActivity.keys.get(i));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    //decides what to do when you press a category
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MainActivity.currentCategory = item.toString();
        category.setText("Category: " + MainActivity.currentCategory);
        return true;
    }
}
