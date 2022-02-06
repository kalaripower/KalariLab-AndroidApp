package com.example.kalarilab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ProfileInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button continueBtn;
   private androidx.appcompat.widget.Toolbar toolbar;
    private LinkedList list;
    private Node currentNode ;
    private KalariLabServices kalariLabServices;
    private UserInfoRegisterSync userInfoRegisterSync = new UserInfoRegisterSync();
    private ProgressBar progressBar;

    private Fragment profileNameFragment, ageFragment, genderFragment, avatarFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        init();
        runFragment(currentNode);





    }


    private void init() {
        list = new LinkedList();
        profileNameFragment = new ProfileNameFragment();
        ageFragment = new AgeFragment();
        continueBtn = findViewById(R.id.continueBtn);
        genderFragment = new GenderFragment();
        avatarFragment = new AvatarFragment();
        toolbar = findViewById(R.id.topAppBar);
        kalariLabServices = new KalariLabServices(this, userInfoRegisterSync);
        progressBar = findViewById(R.id.progressBar);



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
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.container, node.getValue());
        transaction.addToBackStack(null);

        transaction.commit();




    }
//    private void closeCurrentFragment() {
//
//        this.getSupportFragmentManager().popBackStackImmediate();
//    }

    private void fragmentsLinking() {
        list.insert(new Node(avatarFragment));
        list.insert(new Node(genderFragment));
        list.insert(new Node(ageFragment));
        list.insert(new Node(profileNameFragment));

        currentNode = list.getHead();
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
            Log.d("ApiDebug", "continue");
            if (signUp()){
                progressBar.setVisibility(View.GONE);

            }else {
                Log.d("ApiDebug", "Fail");
            }


        }
    }

    private boolean signUp() {
        String email = Register.user.getEmail();
        String password = Register.user.getPassword();
        String firstName =  Register.user.getFirstName();
        String lastName = Register.user.getLastName();

        kalariLabServices.signUp(email, password, firstName, lastName);
        while (!userInfoRegisterSync.changed()){
            synchronized (userInfoRegisterSync){
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    userInfoRegisterSync.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return userInfoRegisterSync.changed();



    }

    private void moveToMainActivity() {
        Intent intent = new Intent(ProfileInfoActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

}