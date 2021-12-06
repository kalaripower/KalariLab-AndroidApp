package com.example.kalarilab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;


public class LogIn extends AppCompatActivity implements View.OnClickListener {


    private Button logInButt, goToSignUpButton;
    private ImageButton signInGmail, signInFacebook;
    private EditText emailEntry, passwordEntry;
    private TextInputLayout emailEntryParent, passwordEntryParent;
    private ProgressBar progressBar;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int  RC_SIGN_IN = 123;
    public SessionManagement sessionManagement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        init();


    }



    public void init() {
        sessionManagement = new SessionManagement(LogIn.this);
        Log.d("checkSession", sessionManagement.returnSession() + "Penis");

        logInButt = findViewById(R.id.LogIn);
        goToSignUpButton = findViewById(R.id.goToSignUp);
        signInGmail = findViewById(R.id.signInGmail);
        signInFacebook = findViewById(R.id.signInFacebook);
        emailEntry = findViewById(R.id.editTextEmail);
        passwordEntry = findViewById(R.id.editTextPassword);
        emailEntryParent = findViewById(R.id.editTextEmailParent);
        passwordEntryParent = findViewById(R.id.editTextPasswordParent);
        progressBar = findViewById(R.id.progressBar);
        sessionManagement = new SessionManagement(LogIn.this);

        configureGoogleRequest();


        logInButt.setOnClickListener(this);
        goToSignUpButton.setOnClickListener(this);
        signInGmail.setOnClickListener(this);

        emailEntryParent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) emailEntryParent.setBoxStrokeColor(getResources().getColor(R.color.KalariLAbSecondary));
                else {
                    emailEntryParent.setBoxStrokeColor(getResources().getColor(R.color.darkGrey));
                }
            }

        });

        passwordEntryParent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
            case R.id.goToSignUp:
                moveToSignUpActivity();
                break;
            case R.id.signInGmail:
                signInViaGmail();

                break;
            case R.id.signInFacebook:
                break;
        }


    }

    private void configureGoogleRequest(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        createSession();
        moveToMainActivity();
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(LogIn.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }


    private void createSession() {
        sessionManagement.saveSession("#TestTest");

    }


    private void moveToSignUpActivity() {
        Log.d("DebugMoveToSignUp", "S3");

        Intent intent = new Intent(LogIn.this, Register.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Log.d("DebugMoveToSignUp", "S3");
        startActivity(intent);
        Log.d("DebugMoveToSignUp", "S44");

    }






    @Override
    protected void onStart() {
        super.onStart();
        checkSession();

    }

    //The following method handles sessions on the current device

    private void checkSession(){

        if(sessionManagement.returnSession() != "") {
            Log.d("checkSession", "LoggedAlready");

            startActivity(new Intent(LogIn.this, MainActivity.class));

        }else {
            Log.d("checkSession", "Did not log in");

        }


    }
    private void signInViaGmail() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            createSession();
            moveToMainActivity();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
        }
    }



}