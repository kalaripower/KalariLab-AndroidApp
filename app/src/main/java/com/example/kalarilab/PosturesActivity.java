package com.example.kalarilab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

public class PosturesActivity extends AppCompatActivity {
    ImageView avatar;
    ListView posturesList;
    User user;
    CustomPosturesAdapter customPosturesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postures);
        init();



    }


    private void init(){
      avatar = findViewById(R.id.avatar);
      posturesList = findViewById(R.id.list);
      user = new User();
        Log.d("DebugList","0");
        Log.d("DebugList","List" + user.progressTrackingSystem.posturesLevels);


        customPosturesAdapter = new CustomPosturesAdapter(this, posturesHashtableKeys(), posturesHashtableValues());
        Log.d("DebugList","2");

      posturesList.setAdapter(customPosturesAdapter);
      //avatar.setImageBitmap(user.getAvatar());
      Log.d("DebugList","List" + user.progressTrackingSystem.posturesLevels.toString());
    }


    private Integer[] posturesHashtableKeys(){
        Integer[] keys = new Integer[user.progressTrackingSystem.posturesLevels.keySet().size()];
        keys = user.progressTrackingSystem.posturesLevels.keySet().toArray(keys);
        return keys;
    }
    private Integer[] posturesHashtableValues(){
        Integer[] values = new Integer[user.progressTrackingSystem.posturesLevels.values().size()];
        values = user.progressTrackingSystem.posturesLevels.values().toArray(values);
        return  values;
    }


}