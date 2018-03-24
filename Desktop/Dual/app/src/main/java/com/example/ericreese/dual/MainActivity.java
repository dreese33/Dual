package com.example.ericreese.dual;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button loginButton1;
    private Button signUpButton1;
    public static DatabaseReference mReference;

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
    }
}
