package com.example.ericreese.dual.Matches;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ericreese.dual.HomeActivity;
import com.example.ericreese.dual.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.example.ericreese.dual.MainActivity;
import com.google.firebase.database.ValueEventListener;
import com.example.ericreese.dual.R;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mMatchesAdapter;
    private RecyclerView.LayoutManager mMatchesLayoutManager;
    private Button back;
    private Button signout;
    //private String cusrrentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        //cusrrentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mMatchesLayoutManager = new LinearLayoutManager(MatchesActivity.this);
        mRecyclerView.setLayoutManager(mMatchesLayoutManager);
        mMatchesAdapter = new MatchesAdapter(getDataSetMatches(), MatchesActivity.this);
        mRecyclerView.setAdapter(mMatchesAdapter);

        getUserMatchId();

        this.back = (Button) this.findViewById(R.id.back);
        this.signout = (Button) this.findViewById(R.id.sign_out);
        this.back = (Button) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to previous screen
                if (MainActivity.newUser == false) {
                    Context context = view.getContext();
                    Intent goToHomeScreen = new Intent(context, HomeActivity.class);
                    MainActivity.loggedIn = true;
                    startActivity(goToHomeScreen);
                    finish();
                } else {
                    //MainActivity.mReference.child("users").child(MainActivity.username).removeValue();
                    //Remove value from database when possible
                    Context context = view.getContext();
                    Intent goToSignUpActivityScreen = new Intent(context, SignUpActivity.class);
                    MainActivity.loggedIn = true;
                    startActivity(goToSignUpActivityScreen);
                    finish();
                }
            }
        });

        this.signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent signout = new Intent(context, MainActivity.class);
                MainActivity.loggedIn = false;
                startActivity(signout);
                finish();

            }
        });
    }

    private void getUserMatchId() {
        //DatabaseReference matchDb = MainActivity.mReference.child("users").child(MainActivity.username).child("connections").child("matches");
        DatabaseReference matchDb = MainActivity.mReference.child("categories").child(MainActivity.currentCategory).child("users").child(MainActivity.username).child("Matches");
        //DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(cusrrentUserID).child("connections").child("matches");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Log.d(dataSnapshot.toString(), "Exists");
                    for(DataSnapshot match : dataSnapshot.getChildren()){
                        FetchMatchInformation(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void FetchMatchInformation(String key) {
        DatabaseReference userDb = MainActivity.mReference;
        final String newKey = key;
//        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String userId = newKey;
                    String name = "";
                    String profileImageUrl = "";
                    //if(dataSnapshot.child("name").getValue()!=null){
                    Object child1 = dataSnapshot.child("users").child(newKey).child("Profile").child("Name").getValue();
                    if (child1 != null) {
                        name = child1.toString();
                    }
                    Object child2 = dataSnapshot.child("categories").child(MainActivity.currentCategory).child("users").child(newKey).child("Image").getValue();
                    if(child2 != null){
                        profileImageUrl = child2.toString();
                        Log.d("TAG", profileImageUrl);
                    }

                    MatchesObject obj = new MatchesObject(userId, name, profileImageUrl);
                    resultsMatches.add(obj);
                    mMatchesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private ArrayList<MatchesObject> resultsMatches = new ArrayList<MatchesObject>();
    private List<MatchesObject> getDataSetMatches() {
        return resultsMatches;
    }

}
