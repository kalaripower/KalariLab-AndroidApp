package com.example.kalarilab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

public class Register extends AppCompatActivity implements View.OnClickListener {


        private static final String TAG = "authDebug";
        private Button goToSignInBtn, registerBtn;
        private EditText  emailEntry, passwordEntry;
        private ProgressBar progressBar;
        private TextInputLayout emailEntryParent, passwordEntryParent;
        private ImageButton signInGmailBtn, signInFacebookBtn;
        private GoogleSignInClient mGoogleSignInClient;
        public SessionManagement sessionManagement;

        private final static int RC_SIGN_IN = 123;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_register);
                init();


        }

        private void init() {

                registerBtn = findViewById(R.id.register);
                goToSignInBtn = findViewById(R.id.goToSignIn);
                emailEntry = findViewById(R.id.editTextEmail);
                passwordEntry = findViewById(R.id.editTextPassword);
                progressBar = findViewById(R.id.progressBar);
                emailEntryParent = findViewById(R.id.editTextEmailParent);
                passwordEntryParent = findViewById(R.id.editTextPasswordParent);
                signInGmailBtn = findViewById(R.id.signInGmail);
                signInFacebookBtn = findViewById(R.id.signInFacebook);
                sessionManagement = new SessionManagement(Register.this);

                goToSignInBtn.setOnClickListener(this);
                signInGmailBtn.setOnClickListener(this);
                signInFacebookBtn.setOnClickListener(this);
                registerBtn.setOnClickListener(this);
                configureGoogleRequest();
                finishAfterRegistrationCompletion();


                emailEntryParent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                                if (hasFocus)
                                        emailEntryParent.setBoxStrokeColor(getResources().getColor(R.color.KalariLAbSecondary));
                                else {
                                        emailEntryParent.setBoxStrokeColor(getResources().getColor(R.color.darkGrey));
                                }
                        }

                });
                passwordEntryParent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                                if (hasFocus)
                                        emailEntryParent.setBoxStrokeColor(getResources().getColor(R.color.KalariLAbSecondary));
                                else {
                                        emailEntryParent.setBoxStrokeColor(getResources().getColor(R.color.darkGrey));
                                }
                        }

                });
        }


        @Override
        public void onClick(View v) {
                switch (v.getId()) {
                        case R.id.goToSignIn:
                                moveToSignInActivity();
                                break;
                        case R.id.register:
                                checkInfo();
                                break;
                        case R.id.signInGmail:
                                signUpViaGmail();
                                break;
                }
        }

        private void moveToSignInActivity() {
                Intent intent = new Intent(this, LogIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
        }

        private void moveToProfileInfoActivity() {
                Intent intent = new Intent(Register.this, ProfileInfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
        }

        private void checkInfo() {
                final String email = this.emailEntry.getText().toString().trim();
                final String password = this.passwordEntry.getText().toString().trim();




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
                }

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                progressBar.setVisibility(View.VISIBLE);
                checkIfValid(email, password);

        }


        private void checkIfValid(final String email, final String password
                                  ) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                //createSession();
                moveToProfileInfoActivity();
                finish();


        }

    private void moveToMainActivity() {
        Intent intent = new Intent(Register.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }


        private void createSession() {
                sessionManagement.saveSession("#TestTest");

        }


        private void configureGoogleRequest() {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        }

        private void signUpViaGmail() {
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


        private void finishAfterRegistrationCompletion() {
                BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                                String action = intent.getAction();
                                if (action.equals("finish_activity")) {
                                        finish();
                                }
                        }
                };
                registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));
        }
}
