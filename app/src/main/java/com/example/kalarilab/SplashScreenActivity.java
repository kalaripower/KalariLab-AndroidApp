package com.example.kalarilab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    private Timer timer;
    private  Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initHooks();

    }

    private void initHooks() {
        context = this;
        timer = new Timer();
        timer.schedule(new RemindTask(), 3000);
    }
    class RemindTask extends TimerTask{

        @Override
        public void run() {
            this.moveToSignInActivity();
        }
        private void moveToSignInActivity() {
            Intent intent = new Intent(context, LogIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
    }
}