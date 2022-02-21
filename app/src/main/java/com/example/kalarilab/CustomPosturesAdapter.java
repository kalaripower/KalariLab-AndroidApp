package com.example.kalarilab;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomPosturesAdapter extends ArrayAdapter<Integer> {
    private Integer[] posturesTags;
    private Integer[] posturesImages;
    private Activity context;
    private SeekBar seekBar;


    public CustomPosturesAdapter(Activity context, Integer[] posturesTags, Integer[] posturesImages) {
        super(context, R.layout.list_items, posturesTags);
        this.context = context;
        this.posturesTags = posturesTags;
        this.posturesImages = posturesImages;
        Log.d("SeekBarDebug", "3");

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_items, null, true);
        seekBar = (SeekBar) rowView.findViewById(R.id.seekBar);
        TextView title = rowView.findViewById(R.id.textViewTitle);
        ImageView posture = rowView.findViewById(R.id.imageViewPosture);

        title.setText(getPostureNameFromTag(posturesTags[position]));

        //Get image from cloud..To be done after backend is done
        posture.setImageResource(getPostureImageFromTag(posturesTags[position], posturesImages[position]));
        return rowView;
    }

    public String getPostureNameFromTag(int keyTag){
        switch (keyTag){
            case 0:
                return "Lion Posture";
            case 1:
                return "Elephant Posture";
        }
        //to be changed later
        return "0";
    }
    public int getPostureImageFromTag(int keyTag, int valueTag){
        //All the postures are not ready yet
        seekBar.setProgress(valueTag * 100);
        switch (keyTag){
            case 0:
                if (valueTag == 0){


                }
                else if(valueTag == 1){

                }
                else if (valueTag == 2){

                }else {

                }

            case 1:
                if (valueTag == 0){

                }
                else if(valueTag == 1){

                }
                else if (valueTag == 2){

                }else {

                }
                break;
            case 2:
                if (valueTag == 0){

                }
                else if(valueTag == 1){


                }
                else if (valueTag == 2){

                }else {

                }
                break;
            case 3:
                if (valueTag == 0){

                }
                else if(valueTag == 1){


                }
                else if (valueTag == 2){

                }else {

                }
                break;
            case 4:
                if (valueTag == 0){

                }
                else if(valueTag == 1){

                }
                else if (valueTag == 2){

                }else {

                }
                break;
            case 5:
                if (valueTag == 0){

                }
                else if(valueTag == 1){


                }
                else if (valueTag == 2){

                }else {

                }
                break;
            case 6:
                if (valueTag == 0){

                }
                else if(valueTag == 1){

                }
                else if (valueTag == 2){

                }else {

                }
                break;
            case 7:
                if (valueTag == 0){

                }
                else if(valueTag == 1){

                }
                else if (valueTag == 2){

                }else {

                }
                break;
        }
       return 0;
    }

}
