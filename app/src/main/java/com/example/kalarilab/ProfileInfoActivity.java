package com.example.kalarilab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class ProfileInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button continueBtn;
    private Toolbar toolbar;
    private LinkedList list;
    private Node currentNode;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        init();
        runFragment(currentNode);

    }


    private void init() {
        continueBtn = findViewById(R.id.continueBtn);
        toolbar = findViewById(R.id.topAppBar);
        list = new LinkedList();
        fragmentInstantiation();
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
        node = list.getHead();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.container, node.getValue());
        transaction.addToBackStack(null);
        transaction.commit();



    }

    private void fragmentInstantiation() {

        Fragment profileNameFragment = new ProfileNameFragment();
        list.insert(new Node(profileNameFragment));


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