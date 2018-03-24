package com.example.ericreese.dual;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by ericreese on 3/24/18.
 */

public class EditProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private Button editProfile;
    private EditText name;
    private EditText bio;
    private Button back;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        this.profileImage = (ImageView) findViewById(R.id.profileImage);
        this.editProfile = (Button) findViewById(R.id.edit_profile_picture);
        this.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open photos, and load selected into profileImage
            }
        });

        this.name = (EditText) findViewById(R.id.name);

        this.bio = (EditText) findViewById(R.id.bio);
        this.back = (Button) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to previous screen
                //TODO: Implement at end of project
            }
        });

        this.confirm = (Button) findViewById(R.id.confirm);
        this.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update values to firebase
            }
        });
    }
}
