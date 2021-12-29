package com.example.kalarilab;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

;


public class OnBoarding extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private LinearLayout dotsLayout;
    private ViewPager viewPager;
    private SliderAdapter sliderAdapter;
    private SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        initHooks();
        onBoardingAlreadySeen();

    }

    private void onBoardingAlreadySeen() {
        sessionManagement.setOnBoardingAlreadySeen();
    }


    private void initHooks() {
       viewPager = findViewById(R.id.viewPager);
       dotsLayout = findViewById(R.id.dots);
       sliderAdapter = new SliderAdapter(this);
        sessionManagement = new SessionManagement(this);
        viewPager.setAdapter(sliderAdapter);
    }
}