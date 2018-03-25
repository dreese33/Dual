package com.example.ericreese.dual;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.example.ericreese.dual.Cardss.*;
import java.util.ArrayList;
import java.util.List;

public class SwipeCardsActivity extends AppCompatActivity {

    private Cards cards_data[];
    private ArrayAdapterr arrayAdapterr;
    private int i;
    private FirebaseAuth mAuth;
    private String currentUId;
    private DatabaseReference usersDb;
    ListView listView;
    List<Cards> rowItems;


    private Button back;
    private Button signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_main);
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


        /***
        * Populate the cards
        *
        * **/
        usersDb = MainActivity.mReference.child("users");
        rowItems = new ArrayList<Cards>();

        arrayAdapterr = new ArrayAdapterr(this, R.layout.item_card, rowItems);

        populateUsers();


        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapterr);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapterr.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                makeToast(SwipeCardsActivity.this, "Left!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(SwipeCardsActivity.this, "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                /*// Ask for more data here
                rowItems.add("XML ".concat(String.valueOf(i)));
                arrayAdapterr.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;*/
                makeToast(SwipeCardsActivity.this, "Out of people!");
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                //View view = flingContainer.getSelectedView();
                //view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                //view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(SwipeCardsActivity.this, "Clicked!");
            }
        });

    }

    public void populateUsers() {

    }

    static void makeToast(Context ctx, String s){
        Toast a = Toast.makeText(ctx, s, Toast.LENGTH_SHORT);
        a.show();


    }

//    @OnClick(R.id.right)
//    public void right() {
//        /**
//         * Trigger the right event manually.
//         */
//        flingContainer.getTopCardListener().selectRight();
//    }
//
//    @OnClick(R.id.left)
//    public void left() {
//        flingContainer.getTopCardListener().selectLeft();
//    }
//
//

}