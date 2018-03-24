package com.example.ericreese.dual;

/**
 * Created by ericreese on 3/24/18.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.text.TextUtils;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameView;
    private EditText passwordView;
    private Button cancel;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        this.cancel = (Button) this.findViewById(R.id.cancelButton);
        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent goToWelcomeScreen = new Intent(context, MainActivity.class);
                startActivity(goToWelcomeScreen);
                finish();
            }
        });

        this.signIn = (Button) findViewById(R.id.email_sign_in_button);
        this.signIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        this.usernameView = (EditText) findViewById(R.id.sign_in);
        this.passwordView = (EditText) findViewById(R.id.password);

    }

        /**
         * Attempts to sign in or register the account specified by the login form.
         * If there are form errors (invalid email, missing fields, etc.), the
         * errors are presented and no actual login attempt is made.
         */
    private void attemptLogin() {

        // Reset errors.
        usernameView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        final String username = usernameView.getText().toString();
        final String password = passwordView.getText().toString();

        boolean cancel = false;
        final View[] focusView = {null};

        if (TextUtils.isEmpty(username)) {
            usernameView.setError("Field is required");
            focusView[0] = usernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            usernameView.setError("Invalid username");
            focusView[0] = usernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error
            focusView[0].requestFocus();
        } else {
            focusView[0] = passwordView;

            MainActivity.mReference.child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChild(username)) {
                        if (password.equals(dataSnapshot.child(username).child("Profile").child("Password").getValue())) {
                            //Signing in
                            Log.d("login", "login successful");
                            Context context = usernameView.getContext();
                            Intent goToMainScreen = new Intent(context, MainActivity.class);
                            MainActivity.loggedIn = true;
                            startActivity(goToMainScreen);
                            finish();
                        } else {
                            passwordView.setError("Password incorrect");
                            focusView[0].requestFocus();
                        }
                    } else {
                        usernameView.setError("User does not exist");
                        focusView[0].requestFocus();
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    focusView[0].requestFocus();
                }

            });
        }
    }

    private boolean isUsernameValid(String username) {
        if (username.length() > 5 && (!(username.contains("#") || username.contains("@") || username.contains("[") || username.contains("]") || username.contains(",") || username.contains("$")))) {
            return true;
        }
        return false;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Make secure password
        return password.length() > 4;
    }
}
