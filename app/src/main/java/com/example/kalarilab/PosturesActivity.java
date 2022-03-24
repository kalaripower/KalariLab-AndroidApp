package com.example.kalarilab;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class PosturesActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomPosturesAdapter customPosturesAdapter;
    private ProgressTrackingSystem progressTrackingSystem;
    private androidx.appcompat.widget.Toolbar toolbar;
    private ViewPager viewPager;
    private FrameLayout container;
    private static final int NUM_PAGES = 5;
    private PagerAdapter pagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posture);

        initHooks();
        bindings();



    }

    private void bindings() {

        progressTrackingSystem.getPosturesLevelsFromDB();
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


    private void initHooks(){

      progressTrackingSystem = new ProgressTrackingSystem();
      customPosturesAdapter = new CustomPosturesAdapter(this, posturesHashtableKeys(), posturesHashtableValues());

        toolbar = findViewById(R.id.topAppBar);
        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        container = findViewById(R.id.container);

    }

    private void moveToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
           viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton:
                finish();
                break;
        }
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new PostureFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}