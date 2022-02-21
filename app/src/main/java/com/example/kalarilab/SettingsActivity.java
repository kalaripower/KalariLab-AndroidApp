package com.example.kalarilab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView changePasswordCardView;
    private ImageButton backBtn;
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
    }

    private void initHooks() {
        changePasswordCardView = findViewById(R.id.changePasswordCard);
        backBtn = findViewById(R.id.backButton);

    }
    private void moveToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
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

        }
    }
}