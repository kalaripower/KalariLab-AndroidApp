package com.example.kalarilab;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener   {

    private ImageButton pressRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pressRecord = findViewById(R.id.recButton);
        pressRecord.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recButton:
               moveToVideoRecorderActivity();
                break;



    }
}

    private void moveToVideoRecorderActivity() {
        Intent intent = new Intent(MainActivity.this, VideoRecorder.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();

    }


}