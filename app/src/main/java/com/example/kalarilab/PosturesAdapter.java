package com.example.kalarilab;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PosturesAdapter extends FragmentPagerAdapter {
    private Integer[] posturesTags;
    private Integer[] posturesImages;
    private Activity context;
    private SeekBar seekBar;
    private boolean doNotifyDataSetChangedOnce = false;
    private View rowView;
    ImageView postureImage;


    private int NUM_ITEMS ;
    private PosturesActivity posturesActivity;
    public PosturesAdapter(FragmentManager fragmentManager, Activity context, PosturesActivity posturesActivity, Integer[] posturesTags, Integer[] posturesImages ) {
        super(fragmentManager);
        this.posturesActivity = posturesActivity;
        this.posturesTags = posturesTags;
        this.posturesImages = posturesImages;
        this.context = context;


    }




    public String getPostureNameFromTag(int keyTag){
        switch (keyTag){
            case 2:
                return "Horse Posture";
            case 3:
                return "Elephant Posture";
        }
        //to be changed later
        return "0";
    }
    public void getPostureImageFromTag(int keyTag, int valueTag) {
        //All the postures are not ready yet
        seekBar.setProgress(valueTag * 100);
        Log.d("posturesDebug", String.valueOf(keyTag));
        switch (keyTag) {
            case 2:
                postureImage.setImageResource(R.drawable.a2);
                break;
            case 3:
                postureImage.setImageResource(R.drawable.a3);
                break;
            case 4:
                postureImage.setImageResource(R.drawable.a4);
                break;
            case 5:
                postureImage.setImageResource(R.drawable.a5);
                break;
            case 6:
                postureImage.setImageResource(R.drawable.a6);
                break;


        }

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        setUpFragmentLayout(position);
        return PostureFragment.newInstance("0", "postureInstance", rowView);

    }

    private void setUpFragmentLayout(int position) {
        LayoutInflater inflater = context.getLayoutInflater();
        rowView = inflater.inflate(R.layout.fragment_posture, null, true);
        seekBar = (SeekBar) rowView.findViewById(R.id.seekBar);
        TextView title = rowView.findViewById(R.id.PostureTitle);
        postureImage = rowView.findViewById(R.id.PostureImage);
        title.setText(getPostureNameFromTag(posturesTags[position]));
        getPostureImageFromTag(posturesTags[position], posturesImages[position]);
    }

    @Override
    public int getCount() {
        if (doNotifyDataSetChangedOnce) {
            doNotifyDataSetChangedOnce = false;
            notifyDataSetChanged();
        }

        return NUM_ITEMS;
    }
    public void setNUM_ITEMS(int NUM_ITEMS) {
        doNotifyDataSetChangedOnce = true;
        this.NUM_ITEMS = NUM_ITEMS;
    }
}
