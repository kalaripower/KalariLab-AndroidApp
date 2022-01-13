package com.example.kalarilab;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener   {


    private Button signOutButton;
    private GoogleSignInClient mGoogleSignInClient;
    private SessionManagement sessionManagement;
    private ProgressTrackingSystem progressTrackingSystem;
    private CircularProgressIndicator circularProgressIndicatorClasses , circularProgressIndicatorPoints;
    private TextView classesProgressText , levelsProgressText,  weeklyPoints, totalPoints;
    private CardView posturesCard;
    private BottomNavigationView bottomNavigationView;
    private ColorStateList navigationViewColorStateList;
    private Streak streak;

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
        Log.d("debugMainActivity", "s0");

        init();
        Log.d("debugMainActivity", "s1");
        configureGoogleRequest();



    }

//    private void runFragments() {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//
//        transaction.add(R.id.container,progressFragment );
//        transaction.add(R.id.container,postureFragment );
//
//        transaction.addToBackStack(null);
//
//        transaction.commit();
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.signOutBtn:
                signOut();
                break;
            case R.id.posturesCard:
                goToPosturesActivity();

                break;

        }
    }

    private void goToPosturesActivity() {
        Intent intent = new Intent(MainActivity.this, PosturesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();

    }

    private void init(){
        signOutButton = findViewById(R.id.signOutBtn);
        signOutButton.setOnClickListener(this);
        sessionManagement = new SessionManagement(MainActivity.this);
        progressTrackingSystem = new ProgressTrackingSystem();
        circularProgressIndicatorClasses = findViewById(R.id.progressCircleClasses);
        classesProgressText = findViewById(R.id.classesProgressText);
        levelsProgressText = findViewById(R.id.levelsProgressText);
        posturesCard = findViewById(R.id.posturesCard);
        totalPoints = findViewById(R.id.totalPoints);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navigationViewColorStateList = new ColorStateList(states, colors);
        circularProgressIndicatorPoints = findViewById(R.id.progressCirclePoints);
        weeklyPoints = findViewById(R.id.weeklyPoints);
        streak = new Streak(progressTrackingSystem, this);
        circularProgressIndicatorClasses.setMax(7);
        totalPoints.setText(String.valueOf(progressTrackingSystem.getTotalPoints()));
        weeklyPoints.setText(String.valueOf(progressTrackingSystem.getWeeklyPoints()));
        circularProgressIndicatorPoints.setProgress(streak.getWeekProgress());
        posturesCard.setOnClickListener(this);
        classesProgressText.setText(new StringBuilder().append(getClassReached()).append("/").append(getNumOfClasses()).toString());
        levelsProgressText.setText(new StringBuilder().append("Level ").append(getLevelReached()).toString());
        circularProgressIndicatorClasses.setMax(getNumOfClasses());
        circularProgressIndicatorClasses.setProgress(getClassReached());
        bottomNavigationView.setItemIconTintList(navigationViewColorStateList);

    }


    private void configureGoogleRequest(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void moveToVideoRecorderActivity() {
        Intent intent = new Intent(MainActivity.this, VideoRecorder.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();

    }

    private void signOut() {
        Log.d("DebugLogout", "0 started" + sessionManagement);

        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.d("DebugLogout", "1" + sessionManagement.returnSession());
                    sessionManagement.removeSession();
                    Log.d("DebugLogout", "2" + sessionManagement.returnSession());

                    startActivity(new Intent(MainActivity.this, LogIn.class));
                    sendBroadcastToPreventAccessToAllActivities();
                    finish();
                }
                else {
                    Log.d("DebugLogout", "3  Failed" + sessionManagement.returnSession());

                }

            }

        });
    }

    private void sendBroadcastToPreventAccessToAllActivities() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
        sendBroadcast(broadcastIntent);
    }


    private int getLevelReached(){
        //Gets reached level from DB
        return 7;
    }
    private int getClassReached(){
        //Gets reached class from cloud
        return 11;
    }
    private int getNumOfClasses() {
        //Gets number of classes from cloud
        return 22;
    }

}

