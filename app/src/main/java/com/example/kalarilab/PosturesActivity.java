package com.example.kalarilab;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class PosturesActivity extends AppCompatActivity implements View.OnClickListener {

    private PosturesAdapter posturesAdapter;
    private ProgressTrackingSystem progressTrackingSystem;
    private androidx.appcompat.widget.Toolbar toolbar;
    private ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posture);

        initHooks();
        bindings();



    }
    private void initHooks(){

        progressTrackingSystem = new ProgressTrackingSystem();
        progressTrackingSystem.getPosturesLevelsFromDB();
        posturesAdapter = new PosturesAdapter( getSupportFragmentManager(), this,this, posturesHashtableKeys(), posturesHashtableValues());

        toolbar = findViewById(R.id.topAppBar);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(posturesAdapter);


    }
    private void bindings() {


        posturesAdapter.setNUM_ITEMS(progressTrackingSystem.getNumberOFOpenedPostures());
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.back:
                        moveToMainActivity();
                        break;
                }

                return false;
            }
        });

    }




    private void moveToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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
    public void onBackPressed() {
       moveToMainActivity();
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