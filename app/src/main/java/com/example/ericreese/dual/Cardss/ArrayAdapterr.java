package com.example.ericreese.dual.Cardss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;


import com.bumptech.glide.Glide;
import com.example.ericreese.dual.FirebaseImageLoader;
import com.example.ericreese.dual.MainActivity;
import com.example.ericreese.dual.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by manel on 9/5/2017.
 */
//Class for fetching the corresponding picture

public class ArrayAdapterr extends ArrayAdapter<Cards> {


    Context context;
    private StorageReference storageReference;
    private FirebaseStorage storage;

    public ArrayAdapterr(Context context, int resourceId, List<Cards> items){
        super(context, resourceId, items);

    }
    public View getView(int position, View convertView, ViewGroup parent){
        Cards card_item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_card, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("images/" + card_item.getProfileImageUrl());

        name.setText(card_item.getName());
        Glide.clear(image);
        if (card_item.getProfileImageUrl() == null) {
            android.util.Log.d("TAG",card_item.getProfileImageUrl());
            Glide.with(convertView.getContext()).load(R.mipmap.ic_launcher).into(image);
        } else {
            Glide.with(convertView.getContext()).using(new FirebaseImageLoader()).load(storageReference).into(image);
        }


        return convertView;

    }

}

