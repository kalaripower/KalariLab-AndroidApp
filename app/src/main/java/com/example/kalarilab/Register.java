package com.example.kalarilab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity implements View.OnClickListener {

  //  private DatabaseReference reference;
    private Button goToSignIn;
    private EditText fullnameEntry, emailEntry, passwordEntry;
    private ProgressBar progressBar;
    private TextInputLayout fullnameEntryParent, emailEntryParent, passwordEntryParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        Resources res = getResources();
        goToSignIn = findViewById(R.id.goToSignIn);
        fullnameEntry = findViewById(R.id.editTextFullName);
        emailEntry = findViewById(R.id.editTextEmail);
        passwordEntry = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progressBar);
        emailEntryParent =findViewById(R.id.editTextEmailParent);
        passwordEntryParent = findViewById(R.id.editTextPasswordParent);
        fullnameEntryParent = findViewById(R.id.editTextFullNameParent);



        goToSignIn.setOnClickListener(this);







    }

    

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goToSignIn:
               moveToSignInActivity();

                break;
            case R.id.register:
                refactorInfo();
                break;
        }
    }
    private void moveToSignInActivity() {
        Intent intent = new Intent(this, LogIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }


    private void refactorInfo() {
        final String fullName = this.fullnameEntry.getText().toString().trim();
        final String email = this.emailEntry.getText().toString().trim();
        final String password = this.passwordEntry.getText().toString().trim();


        if (fullName.isEmpty()) {
           fullnameEntryParent.setBoxStrokeColor(getResources().getColor(R.color.red));
           return;
        }

        if (email.isEmpty()) {
            emailEntryParent.setBoxStrokeColor(getResources().getColor(R.color.red));

            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEntryParent.setBoxStrokeColor(getResources().getColor(R.color.red));

            return;
        }
        if (password.isEmpty()) {
           passwordEntryParent.setBoxStrokeColor(getResources().getColor(R.color.red));

            return;
        }
        if (password.length() < 6) {
             passwordEntryParent.setBoxStrokeColor(getResources().getColor(R.color.red));

            return;


           getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                   WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setVisibility(View.VISIBLE);
            checkIfValid(email, password, fullName, username, occupation);




    }

    private void checkIfValid(final String email, final String password,
                              final String fullName, final String username, final String occupation) {
        reference = FirebaseDatabase.getInstance().getReference();
        Query userQuery = reference.child("Users").orderByChild("email").equalTo(email);
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    notifyUserEmailTaken();
                } else {
                    progressBar.setVisibility(View.GONE);
                    passInfoToNextActivity(email, password, fullName, username, occupation);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void passInfoToNextActivity(String email, String password, String fullname, String username, String occupation) {
        Intent passInfo = new Intent(Register.this,PersonalInfo.class);
        passInfo.putExtra("email", email);
        passInfo.putExtra("password", password);
        passInfo.putExtra("fullname", fullname);
        passInfo.putExtra("username", username);
        passInfo.putExtra("occupation", occupation);
        startActivity(passInfo);

    }

    private void notifyUserEmailTaken() {
        new MaterialAlertDialogBuilder(Register.this)
                .setTitle("Sign up" + "...")
                .setMessage(messagesAdapter.getItem(2).toString())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }



    TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            checkIfUsernameTaken();

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void afterTextChanged(Editable s) {
            checkIfUsernameTaken();

        }

    };



    private void checkIfUsernameTaken() {
        final String username = '@' + this.username.getText().toString().trim();
        Query usernameQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("username").equalTo(username);
        usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    usernameNotTaken.set(false);
                    notifyUsernameTaken();
                    usernameNotTaken.set(false);

                }else{
                    usernameNotTaken.set(true);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void notifyUsernameTaken() {
        this.username.setError(messagesAdapter.getItem(4).toString());
        this.username.requestFocus();

    }

    private void finishAfterRegistrationCompletion() {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals("finish_activity")){
                    finish();
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));
    }


}