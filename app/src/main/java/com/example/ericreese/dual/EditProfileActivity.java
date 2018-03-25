package com.example.ericreese.dual;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;

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
    private Uri uri;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private ArrayList<String> users = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        this.profileImage = (ImageView) findViewById(R.id.profileImage);

        //TODO: Test this code!
        if (MainActivity.newUser == false && MainActivity.image != null) {
            StorageReference ref = storageReference.child("images/" + MainActivity.image);
            Glide.with(this)
                    .using(new FirebaseImageLoader())
                    .load(ref)
                    .into(profileImage);
        }

        this.editProfile = (Button) findViewById(R.id.edit_profile_picture);
        this.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open photos, and load selected into profileImage
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        this.name = (EditText) findViewById(R.id.name);
        this.name.setText(MainActivity.name);
        this.bio = (EditText) findViewById(R.id.bio);
        this.bio.setText(MainActivity.bio);
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

        this.confirm = (Button) findViewById(R.id.confirm);
        this.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update values to firebase
                MainActivity.newUser = false;
                uploadImage();
                updateData();
                Context context = view.getContext();
                Intent goToHomeScreen = new Intent(context, HomeActivity.class);
                MainActivity.loggedIn = true;
                startActivity(goToHomeScreen);
                finish();
            }
        });

        MainActivity.mReference.addChildEventListener(new ChildEventListener() {

            String value = "";
            boolean begin = false;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (users.contains(child.getKey())) {
                        continue;
                    }

                    if (begin) {
                        users.add(child.getKey());
                    } else if (child.getValue().equals("Terminate")) {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            uri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                profileImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates data on firebase with new data
     */
    private void updateData() {
        Object bioObject = bio.getText();
        String bioString = "";
        if (bioObject != null) {
            bioString = bioObject.toString();
        }

        Object nameObject = name.getText();
        String nameString = "";
        if (nameObject != null) {
            nameString = nameObject.toString();
        }

        MainActivity.mReference.child("users").child(MainActivity.username).child("Profile").child("Name").setValue(nameString);
        MainActivity.mReference.child("users").child(MainActivity.username).child("Profile").child("Description").setValue(bioString);
    }

    /**
     * Uploads image to firebase
     */
    private void uploadImage() {

        if(uri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final String id = UUID.randomUUID().toString();
            StorageReference ref = storageReference.child("images/"+ id);
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            MainActivity.mReference.child("users").child(MainActivity.username).child("Profile").child("Image").setValue(id);
                            MainActivity.mReference.child("categories").child("All").child("users").child(MainActivity.username).child("Image").setValue(id);
                            Toast.makeText(EditProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EditProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}

