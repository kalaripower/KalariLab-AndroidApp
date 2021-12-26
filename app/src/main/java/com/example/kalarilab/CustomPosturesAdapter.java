package com.example.kalarilab;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomPosturesAdapter extends ArrayAdapter<Integer> {
    private Integer[] posturesTags;
    private Integer[] posturesImages;
    private Activity context;


    public CustomPosturesAdapter(Activity context, Integer[] posturesTags, Integer[] posturesImages) {
        super(context, R.layout.list_items, posturesTags);
        this.context = context;
        this.posturesTags = posturesTags;
        this.posturesImages = posturesImages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_items, null, true);
        TextView title = rowView.findViewById(R.id.textViewTitle);
        ImageView posture = rowView.findViewById(R.id.imageViewPosture);

        title.setText(getPostureNameFromTag(posturesTags[position]));

        //Get image from cloud..To be done after backend is done
        //posture.setImageResource(posturesImages[position]);
        return rowView;
    }

    public String getPostureNameFromTag(int tag){
        switch (tag){
            case 1:
                return "Lion Posture";
            case 2:
                return "Elephant Posture";
        }
        //to be changed later
        return "0";
    }
}
