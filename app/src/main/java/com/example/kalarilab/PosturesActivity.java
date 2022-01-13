package com.example.kalarilab;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class PosturesActivity extends AppCompatActivity {
    ImageView avatar;
    ListView posturesList;

    CustomPosturesAdapter customPosturesAdapter;
    private static SeekBar seekBar;
    ProgressTrackingSystem progressTrackingSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postures);
        Log.d("SeekBarDebug","2");

        init();



    }


    private void init(){
      avatar = findViewById(R.id.avatar);
      posturesList = findViewById(R.id.list);
      seekBar = (SeekBar) findViewById(R.id.seekBar);
      progressTrackingSystem = new ProgressTrackingSystem();

      progressTrackingSystem.getPosturesLevelsFromDB();



        Log.d("SeekBarDebug", "10");

      customPosturesAdapter = new CustomPosturesAdapter(this, posturesHashtableKeys(), posturesHashtableValues());


        posturesList.setAdapter(customPosturesAdapter);
    }

    public static void setSeekBarProgress(int progress){
        seekBar.setProgress(progress );
    }


    private Integer[] posturesHashtableKeys(){
        Integer[] keys = new Integer[progressTrackingSystem.posturesLevels.keySet().size()];
        keys = progressTrackingSystem.posturesLevels.keySet().toArray(keys);
        return keys;
    }
    private Integer[] posturesHashtableValues(){
        Integer[] values = new Integer[progressTrackingSystem.posturesLevels.values().size()];
        values = progressTrackingSystem.posturesLevels.values().toArray(values);
        return  values;
    }


}