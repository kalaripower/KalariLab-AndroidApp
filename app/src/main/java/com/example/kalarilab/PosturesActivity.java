package com.example.kalarilab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class PosturesActivity extends AppCompatActivity {
    private ImageView avatar;
    private ListView posturesList;
    private String[] listTitles = {"1", "@", "4"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postures);
        init();



    }

    private void init(){
      avatar = findViewById(R.id.avatar);
      posturesList = findViewById(R.id.list);





     // avatar.setImageDrawable(getResources().getDrawable(R.drawable.kalaripayattu));
        
    }
}