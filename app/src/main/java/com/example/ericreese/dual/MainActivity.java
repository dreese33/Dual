package com.example.ericreese.dual;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.ericreese.dual.R;

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

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.show();

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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
