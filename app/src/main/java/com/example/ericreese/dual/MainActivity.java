package com.example.ericreese.dual;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button loginButton1;
    private Button signUpButton1;
    public static DatabaseReference mReference;
    public static boolean loggedIn = false;
    public static String username;
    public static boolean newUser = false;
    public static String image;
    public static String name;
    public static String bio;
    public static ArrayList<String> keys = new ArrayList<String>();

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReference = FirebaseDatabase.getInstance().getReference();
        this.loginButton1 = (Button) this.findViewById(R.id.loginButton);
        this.signUpButton1 = (Button) this.findViewById(R.id.signUpButton);
        this.loginButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent goToLogInScreen = new Intent(context, LoginActivity.class);
                startActivity(goToLogInScreen);
                finish();
            }
        });
        this.signUpButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent goToLogInScreen = new Intent(context, SignUpActivity.class);
                startActivity(goToLogInScreen);
                finish();
            }
        });

        MainActivity.mReference.addChildEventListener(new ChildEventListener() {

            String value = "";

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.getValue().equals("Terminate")) {
                        break;
                    } else if (keys.contains(child.getKey())) {
                        continue;
                    }
                    keys.add(child.getKey());
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
    }
}
