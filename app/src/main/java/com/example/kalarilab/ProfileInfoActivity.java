package com.example.kalarilab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ProfileInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button continueBtn;
    private androidx.appcompat.widget.Toolbar toolbar;
    private LinkedList list;
    private Node currentNode ;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private  Fragment profileNameFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        init();
        runFragment(currentNode);
        Log.d("profileInfoDebug", "s3");



    }


    private void init() {
        list = new LinkedList();
        profileNameFragment = new ProfileNameFragment();
        continueBtn = findViewById(R.id.continueBtn);
        toolbar = findViewById(R.id.topAppBar);


        manager = getSupportFragmentManager();

        transaction = manager.beginTransaction();
        fragmentsLinking();


        continueBtn.setOnClickListener(ProfileInfoActivity.this);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.back:
                        moveToSignUpActivity();
                        break;
                }

              return false;
            }
        });

    }

    private void moveToSignUpActivity() {
        Intent intent = new Intent(ProfileInfoActivity.this, Register.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

    }

    private void runFragment(Node node) {


        transaction.add(R.id.container, node.getValue());
        Log.d("profileInfoDebug", "S1");

        transaction.addToBackStack(null);
        Log.d("profileInfoDebug", "S3");

        transaction.commit();
        Log.d("profileInfoDebug", "S4");




    }

    private void fragmentsLinking() {
        list.insert(new Node(profileNameFragment));
        currentNode = list.getHead();
        Log.d("LinkedListDebug",  list.getHead().toString());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continueBtn:
                goToNextFragment();

                break;
        }
    }

    private void goToNextFragment() {
        if (currentNode.getNext() != null) {
            currentNode = currentNode.getNext();
            runFragment(currentNode);
        }
        else
        {
            moveToMainActivity();
        }
    }
    private void moveToMainActivity() {
        Intent intent = new Intent(ProfileInfoActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

}