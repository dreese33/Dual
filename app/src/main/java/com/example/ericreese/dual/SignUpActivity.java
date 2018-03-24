package com.example.ericreese.dual;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * Created by ericreese on 3/24/18.
 */

public class SignUpActivity extends AppCompatActivity {

    private EditText emailView;
    private EditText usernameView;
    private EditText passwordView;
    private Button cancel;
    private Button signUp;
    private EditText descriptionView;
    private EditText nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        this.cancel = (Button) this.findViewById(R.id.cancelButton);
        this.cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent goToWelcomeScreen = new Intent(context, MainActivity.class);
                startActivity(goToWelcomeScreen);
                finish();
            }
        });

        signUp = (Button) findViewById(R.id.email_sign_in_button);
        signUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        passwordView = (EditText) findViewById(R.id.password);
        emailView = (EditText) findViewById(R.id.email);
        usernameView = (EditText) findViewById(R.id.username);
        nameView = (EditText) findViewById(R.id.name);
        descriptionView = (EditText) findViewById(R.id.description);
    }

    /**
     * Attempts to register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void signUp() {

        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        final String username = usernameView.getText().toString();
        final String password = passwordView.getText().toString();
        final String email = emailView.getText().toString();
        final String description = descriptionView.getText().toString();
        final String name = nameView.getText().toString();

        boolean cancel = false;
        final View[] focusView = {null};

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError("Password is invalid");
            focusView[0] = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError("Field required");
            focusView[0] = emailView;
            cancel = true;
        } else if (!isEmailValid(username)) {
            usernameView.setError("Username is invalid");
            focusView[0] = usernameView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            passwordView.setError("Field required");
            focusView[0] = passwordView;
            cancel = true;
        } else if (TextUtils.isEmpty(description)) {
            descriptionView.setError("Field required");
            focusView[0] = descriptionView;
            cancel = true;
        } else if (TextUtils.isEmpty(name)) {
            nameView.setError("Field required");
            focusView[0] = nameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error
            focusView[0].requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            focusView[0] = passwordView;

            MainActivity.mReference.child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(username)) {
                        MainActivity.mReference.child("users").child(username).child("Profile").child("Password").setValue(password);
                        MainActivity.mReference.child("users").child(username).child("Profile").child("Name").setValue(name);
                        MainActivity.mReference.child("users").child(username).child("Profile").child("Email").setValue(email);
                        MainActivity.mReference.child("users").child(username).child("Profile").child("Description").setValue(description);
                        MainActivity.username = username;

                        //Account created and signing in
                        Context context = emailView.getContext();
                        Intent goToEditProfileScreen = new Intent(context, EditProfileActivity.class);
                        MainActivity.loggedIn = true;
                        startActivity(goToEditProfileScreen);
                        finish();
                    } else {
                        focusView[0].requestFocus();
                        passwordView.setError("This user already exists");
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    focusView[0].requestFocus();
                }

            });
        }
    }

    private boolean isEmailValid(String email) {
        if (email.length() > 5 && (!(email.contains("#") || email.contains("@") || email.contains("[") || email.contains("]") || email.contains(",") || email.contains("$")))) {
            return true;
        }
        return false;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}

