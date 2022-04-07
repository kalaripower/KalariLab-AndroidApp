package com.example.kalarilab;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;


    private BottomNavigationView bottomNavigationView;
    private ColorStateList navigationViewColorStateList;
    private FragmentAdapter fragmentAdapter;


    // FOR NAVIGATION VIEW ITEM ICON COLOR
    int[][] states = new int[][]{
            new int[]{-android.R.attr.state_checked},  // unchecked
            new int[]{android.R.attr.state_checked},   // checked
            new int[]{}                                // default
    };
    // Fill in color corresponding to state defined in state
    int[] colors = new int[]{
            Color.parseColor("#aaa8a8"),
            Color.parseColor("#ce262f"),
            Color.parseColor("#aaa8a8"),
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initHooks();
        bindings();
        receiveIntentFragment();


    }





    private void initHooks() {



        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navigationViewColorStateList = new ColorStateList(states, colors);
        viewPager = findViewById(R.id.viewPager);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), this);
    }
    private void bindings() {
        bottomNavigationView.setItemIconTintList(navigationViewColorStateList);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateNavigationBarState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(fragmentAdapter);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_page:

                    viewPager.setCurrentItem(0);

                    break;
                case R.id.profile_page:

                   viewPager.setCurrentItem(3);
                    break;
                case R.id.premium_page:

                    viewPager.setCurrentItem(2);
                    break;

                case R.id.classes_page:
                    viewPager.setCurrentItem(1);
            }

            return true;
        });


    }






    private void receiveIntentFragment(){
        Intent intent = getIntent();
        if (intent.hasExtra("frgToLoad")) {


            String intentFragment = getIntent().getExtras().getString("frgToLoad");

            switch (intentFragment) {
                case "PROFILE_FRAGMENT":
                    viewPager.setCurrentItem(3);
                    break;

                default:
                    viewPager.setCurrentItem(0);
                    break;
            }
        }
    }





    public void updateNavigationBarState(int position){
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.getItem(position);
        item.setChecked(true);


    }



}