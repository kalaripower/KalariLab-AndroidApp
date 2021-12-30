package com.example.kalarilab;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private TextView[] dots;
    private   Button getStartedBtn;
    private RelativeLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        initHooks();

        Log.d("onBoardingSeenDebug", sessionManagement.getFRESH_INSTALLStatus());

    }

    private void onBoardingAlreadySeen() {
        sessionManagement.onBoardingSeen();;
    }


    private void initHooks() {
       viewPager = findViewById(R.id.viewPager);
       dotsLayout = findViewById(R.id.dotsLayout);
       sliderAdapter = new SliderAdapter(this);
        sessionManagement = new SessionManagement(this);
        mainLayout = findViewById(R.id.main);
        viewPager.setAdapter(sliderAdapter);
        getStartedBtn = findViewById(R.id.getStartedBtn);

        addDotsIndicators(0);
        viewPager.addOnPageChangeListener(viewListener);
    }

    private void addDotsIndicators(int position) {
        dots = new TextView[3];
        dotsLayout.removeAllViews();
        getStartedBtn.setVisibility(View.GONE);
        for(int i = 0 ; i < dots.length ; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.white));
            dotsLayout.addView(dots[i]);
        }
        if(dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.KalariLAbSecondary));
        }

    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicators(position);
            if (position == 2) {
                addGetStartedButton();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void addGetStartedButton() {
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBoardingAlreadySeen();
                moveToSignInActivity();
            }

        });
        getStartedBtn.setVisibility(View.VISIBLE);

    }



    private void moveToSignInActivity() {
        Intent intent = new Intent(this, LogIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}