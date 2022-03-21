package com.example.kalarilab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView changePasswordCardView, logOutCard;
    private ImageButton backBtn;
     private GoogleSignInClient mGoogleSignInClient;
     private SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initHooks();
        bindings();
    }

    private void bindings() {
        changePasswordCardView.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        logOutCard.setOnClickListener(this);
    }

    private void initHooks() {
        changePasswordCardView = findViewById(R.id.changePasswordCard);
        backBtn = findViewById(R.id.backButton);
        logOutCard = findViewById(R.id.logOutCard);
        sessionManagement = new SessionManagement(this);
    }
    private void moveToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
    private void signOut() {
    try {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    sessionManagement.removeSession();
                    startActivity(new Intent(SettingsActivity.this, LogIn.class));
                    sendBroadcastToPreventAccessToAllActivities();

                }
            }

        });
    } catch (Exception e){
        sessionManagement.removeSession();
        startActivity(new Intent(SettingsActivity.this, LogIn.class));
        sendBroadcastToPreventAccessToAllActivities();
        finish();

    }

    }

    private void sendBroadcastToPreventAccessToAllActivities() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
        sendBroadcast(broadcastIntent);
    }

    private void configureGoogleRequest(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

        @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changePasswordCard:
                Intent intent = new Intent(this, ChangePasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
            case R.id.backButton:
                moveToMainActivity();
                break;
            case R.id.logOutCard:
                signOut();
                break;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}