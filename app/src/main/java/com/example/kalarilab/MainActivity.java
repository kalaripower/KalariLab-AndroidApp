package com.example.kalarilab;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
public class MainActivity extends AppCompatActivity implements View.OnClickListener   {


    private Button signOutButton;
    private GoogleSignInClient mGoogleSignInClient;
    private SessionManagement sessionManagement;
    private Fragment progressFragment, postureFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
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


        }
    }

    private void init(){
        signOutButton = findViewById(R.id.signOutBtn);
        progressFragment = new PTS_ClassProgressFragment();
        postureFragment = new PTS_PostureFragment();
        signOutButton.setOnClickListener(this);
        sessionManagement = new SessionManagement(MainActivity.this);

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



}

