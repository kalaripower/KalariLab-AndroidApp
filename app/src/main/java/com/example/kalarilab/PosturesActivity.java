package com.example.kalarilab;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class PosturesActivity extends AppCompatActivity implements View.OnClickListener {
    private  ImageView avatar;
    private ListView posturesList;
    private CustomPosturesAdapter customPosturesAdapter;
    private ProgressTrackingSystem progressTrackingSystem;
    private ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postures);

        initHooks();
        bindings();



    }

    private void bindings() {

        posturesList.setAdapter(customPosturesAdapter);
        progressTrackingSystem.getPosturesLevelsFromDB();
        backBtn.setOnClickListener(this);

    }


    private void initHooks(){
      avatar = findViewById(R.id.avatar);
      posturesList = findViewById(R.id.list);
      progressTrackingSystem = new ProgressTrackingSystem();
      customPosturesAdapter = new CustomPosturesAdapter(this, posturesHashtableKeys(), posturesHashtableValues());
      backBtn = findViewById(R.id.backButton);

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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton:
                finish();
                break;
        }
    }
}