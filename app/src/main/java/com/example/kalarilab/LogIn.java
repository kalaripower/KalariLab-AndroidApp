package com.example.kalarilab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    private Button logInButt, goToSignUpButton;
    private ImageButton signInGmail, signInFacebook;
    private ImageView backButt;
    private EditText emailEntry, passwordEntry;
    private TextInputLayout emailEntryParent, passwordEntryParent;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        init();


    }

    public void init() {
        logInButt = findViewById(R.id.LogIn);
        goToSignUpButton = findViewById(R.id.goToSignIn);
        signInGmail = findViewById(R.id.signInGmail);
        signInFacebook = findViewById(R.id.signInFacebook);
        backButt = findViewById(R.id.backButton);
        emailEntry = findViewById(R.id.editTextEmail);
        passwordEntry = findViewById(R.id.editTextPassword);
        emailEntryParent = findViewById(R.id.editTextEmailParent);
        passwordEntryParent = findViewById(R.id.editTextPasswordParent);
        progressBar = findViewById(R.id.progressBar);

        logInButt.setOnClickListener(this);
        goToSignUpButton.setOnClickListener(this);
        signInGmail.setOnClickListener(this);
        backButt.setOnClickListener(this);

        emailEntryParent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) emailEntryParent.setBoxStrokeColor(getResources().getColor(R.color.KalariLAbSecondary));
                else {
                    emailEntryParent.setBoxStrokeColor(getResources().getColor(R.color.darkGrey));
                }
            }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LogIn:
                checkEnteredInfo();

                break;
            case R.id.goToSignIn:
                moveToSignUpActivity();
                break;
            case R.id.signInGmail:


                break;
            case R.id.signInFacebook:
                break;
        }


    }


    private void checkEnteredInfo() {
        final String email = this.emailEntry.getText().toString().trim().toLowerCase(Locale.ROOT);
        final String password = this.passwordEntry.getText().toString().trim().toLowerCase(Locale.ROOT);


        if (email.isEmpty()){
            this.emailEntryParent.setBoxStrokeColor(getResources().getColor(R.color.red));

            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.emailEntryParent.setBoxStrokeColor(getResources().getColor(R.color.red));

            return;
        }
        if (password.isEmpty()){
            this.passwordEntryParent.setBoxStrokeColor(getResources().getColor(R.color.red));

            return;
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);
        logIn(email, password);
    }

    private void logIn(String email, String password) {
        createSession();
        moveToMainActivity();
    }
    private void moveToMainActivity() {
        Intent intent = new Intent(LogIn.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }


    private void createSession() {
        SessionManagement sessionManagement = new SessionManagement(LogIn.this);
        sessionManagement.saveSession("#TestTest");

    }


    private void moveToSignUpActivity() {
        Intent intent = new Intent(LogIn.this, Register.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }



    //The following method handles sessions on the current device

    @Override
    protected void onStart() {
        super.onStart();
        checkSession();
    }

    private void checkSession(){
        SessionManagement sessionManagement = new SessionManagement(LogIn.this);
        if(sessionManagement.returnSession() != "") {
            startActivity(new Intent(LogIn.this, MainActivity.class));
        }else {
            //Do nothing.
        }


    }
}