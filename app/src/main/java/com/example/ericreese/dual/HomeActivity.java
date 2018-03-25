package com.example.ericreese.dual;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ericreese on 3/24/18.
 */

public class HomeActivity extends AppCompatActivity {
    static int count = 1;

    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.signIn = (Button) this.findViewById(R.id.profile);

        this.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent goToEditProfile = new Intent(context, EditProfileActivity.class);
                startActivity(goToEditProfile );
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
        for (int i = 0; i<2; i++) {
            menu.add(0, menu.size(), Menu.NONE, R.string.hello_world).setIcon(R.drawable.ic_emoticon);
            count++;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    //decides what to do when you press a category
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
