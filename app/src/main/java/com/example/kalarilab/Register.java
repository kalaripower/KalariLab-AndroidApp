package com.example.kalarilab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;

public class Register extends AppCompatActivity implements View.OnClickListener {


        private static final String TAG = "authDebug";
        private Button goToSignInBtn, registerBtn;
        private EditText  emailEntry, passwordEntry, fullNameEntry, userNameEntry;
        private ProgressBar progressBar;
        private TextInputLayout emailEntryParent, passwordEntryParent, fullNameEntryParent, userNameEntryParent;
        private Button signInGmailBtn, signInFacebookBtn;
        private GoogleSignInClient mGoogleSignInClient;
        public SessionManagement sessionManagement;
        private CallbackManager callbackManager;
        private LoginManager loginManager;
        private TextView warningTextEmail, warningTextPassword, warningTextUserName;
        public static User user;
        private final static int RC_SIGN_IN = 123;
        private KalariLabServices kalariLabServices;
        AlertDialog.Builder alertDialogBuilder;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_register);
                initHooks();
                bindings();


        }

        private void bindings() {
                FacebookSdk.sdkInitialize(Register.this);
                goToSignInBtn.setOnClickListener(this);
                signInGmailBtn.setOnClickListener(this);
                signInFacebookBtn.setOnClickListener(this);
                registerBtn.setOnClickListener(this);
                
                emailEntryParent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                                if (hasFocus) {
                                    emailEntryParent.setBoxStrokeColor(getResources().getColor(R.color.KalariLAbSecondary));
                                }
                                else {
                                        emailEntryParent.setBoxStrokeColor(getResources().getColor(R.color.darkGrey));
                                }
                        }

                });
                emailEntry.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                                if(hasFocus){
                                        warningTextEmail.setText("");
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

            userNameEntry.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        warningTextUserName.setText("");
                    }
                }
            });


          passwordEntry.setOnClickListener(this);
          emailEntry.setOnClickListener(this);
          userNameEntry.setOnClickListener(this);


        }

        private void initHooks() {

                registerBtn = findViewById(R.id.register);
                goToSignInBtn = findViewById(R.id.goToSignIn);
                emailEntry = findViewById(R.id.editTextEmail);
                passwordEntry = findViewById(R.id.editTextPassword);
                progressBar = findViewById(R.id.progressBar);
                emailEntryParent = findViewById(R.id.editTextEmailParent);
                passwordEntryParent = findViewById(R.id.editTextPasswordParent);
                signInGmailBtn = findViewById(R.id.signUpGmail);
                signInFacebookBtn = findViewById(R.id.signUpFacebook);
                sessionManagement = new SessionManagement(Register.this);
                callbackManager = CallbackManager.Factory.create();
                warningTextEmail = findViewById(R.id.warningTextEmail);
                user = new User();
                kalariLabServices = new KalariLabServices(this);
                warningTextPassword = findViewById(R.id.warningTextPassword);
                 alertDialogBuilder = new AlertDialog.Builder(this);
                 fullNameEntryParent  = findViewById(R.id.editTextFullNameParent);
                 fullNameEntry = findViewById(R.id.editTextFullName);
                 userNameEntryParent = findViewById(R.id.editTextUserNameParent);
                 userNameEntry = findViewById(R.id.editTextUserName);
                 warningTextUserName = findViewById(R.id.warningTextUserName);
                configureGoogleRequest();
                finishAfterRegistrationCompletion();
                printHashKey();
                facebookLogin();



        }


        @Override
        public void onClick(View v) {
                switch (v.getId()) {
                        case R.id.goToSignIn:
                                moveToSignInActivity();
                                break;
                        case R.id.register:
                                checkInfo(this.emailEntry.getText().toString(), this.passwordEntry.getText().toString(),
                                        this.fullNameEntry.getText().toString(), this.userNameEntry.getText().toString());
                                break;
                        case R.id.signUpGmail:
                                signUpViaGmail();
                                break;
                        case R.id.signUpFacebook:
                                loginManager.logInWithReadPermissions(
                                        Register.this,
                                        Arrays.asList(
                                                "email",
                                                "public_profile",
                                                "user_birthday"));
                                break;
                    case R.id.editTextEmail:
                        warningTextEmail.setText("");
                        break;
                    case R.id.editTextPassword:
                        warningTextPassword.setText("");
                        break;
                    case R.id.editTextUserName:
                        warningTextUserName.setText("");
                        break;
                }
        }

        private void moveToSignInActivity() {
                Intent intent = new Intent(this, LogIn.class);
                startActivity(intent);
        }

//        private void moveToProfileInfoActivity() {
//                progressBar.setVisibility(View.GONE);
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                Intent intent = new Intent(Register.this, ProfileInfoActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
//
//        }

        private void checkInfo(String email, String password, String fullName, String userName) {
                final String finalEmail = email.toLowerCase(Locale.ROOT).trim();
                final String finalPassword = password.toLowerCase(Locale.ROOT).trim();
                final String finalFullName = fullName.toLowerCase(Locale.ROOT).trim();
                final String finalUserName = userName.toLowerCase(Locale.ROOT).trim();

                if (finalEmail.isEmpty()) {
                        warningTextEmail.setText(R.string.empty_email_field);
                        return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(finalEmail).matches()) {
                        warningTextEmail.setText(R.string.invalid_email);

                        return;
                }
                if (finalPassword.isEmpty()) {
                        warningTextPassword.setText(R.string.empty_password);
                        return;
                }
                if (finalPassword.length() < 6) {
                        warningTextPassword.setText(R.string.short_password);
                        return;
                }
            if (userName.isEmpty()) {
               warningTextUserName.setText(R.string.empty_email_field);
                return;
            }

                progressBar.setVisibility(View.VISIBLE);
                addInfoToUserObject(finalEmail, finalPassword, finalFullName, finalUserName);
                register();
                progressBar.setVisibility(View.GONE);


        }

        private void register() {
            progressBar.setVisibility(View.VISIBLE);
                kalariLabServices.signUp(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getUserName());

        }

        private void addInfoToUserObject(String email, String password, String fullName, String userName) {
                user.setEmail(email);
                user.setPassword(password);
                user.setUserName(userName);
                try {
                        String[] fullNameSplit = fullName.split("\\s+");
                        user.setFirstName(fullNameSplit[0]);
                        user.setLastName(fullNameSplit[1]);
                } catch (Exception e){
                        user.setFirstName(fullName);
                }



        }




    private void moveToMainActivity() {
        Intent intent = new Intent(Register.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }


        private void createSession() {
                sessionManagement.saveSession("#TestTest");

        }

        public void
        printHashKey()
        {

                // Add code to print out the key hash
                try {

                        PackageInfo info
                                = getPackageManager().getPackageInfo(
                                "com.android.facebookloginsample",
                                PackageManager.GET_SIGNATURES);

                        for (Signature signature : info.signatures) {

                                MessageDigest md
                                        = MessageDigest.getInstance("SHA");
                                md.update(signature.toByteArray());
                                Log.d("KeyHashDebug",
                                        Base64.encodeToString(
                                                md.digest(),
                                                Base64.DEFAULT));
                        }
                }

                catch (PackageManager.NameNotFoundException e) {
                }

                catch (NoSuchAlgorithmException e) {
                }
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


        private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
                try {
                        GoogleSignInAccount account = completedTask.getResult(ApiException.class);

                        // Signed in successfully
                        String email = account.getEmail();
                        String password = "/";
                        String fullName = account.getGivenName() + " "+ account.getFamilyName();
                        String userName = "/";


                        checkInfo(email, password, fullName, userName );


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
        public void facebookLogin()
        {

        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(LoginResult loginResult)
        {
                GraphRequest request = GraphRequest.newMeRequest(

                        loginResult.getAccessToken(),

                        new GraphRequest.GraphJSONObjectCallback() {


        @Override
                public void onCompleted(JSONObject object,
                                        GraphResponse response)
                {

                        if (object != null) {
                                try {
                                        String name = object.getString("name");
                                        String email = object.getString("email");
                                        String fbUserID = object.getString("id");

                                        disconnectFromFacebook();

                                        // do action after Facebook login success
                                        // or call your API
                                }
                                catch (JSONException | NullPointerException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        });

                Bundle parameters = new Bundle();
                parameters.putString(
                        "fields",
                        "id, name, email, gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
                }

                @Override
                public void onCancel()
                {
                        Log.v("LoginScreen", "---onCancel");
                }

                @Override
                public void onError(FacebookException error)
                {
                        // here write code when get error
                        Log.v("LoginScreen", "----onError: "
                                + error.getMessage());
                }
        });
        }


        public void disconnectFromFacebook()
        {
                if (AccessToken.getCurrentAccessToken() == null) {
                        return; // already logged out
                }

                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/permissions/",
                        null,
                        HttpMethod.DELETE,
                        new GraphRequest
                                .Callback() {
                                @Override
                                public void onCompleted(GraphResponse graphResponse)
                                {
                                        LoginManager.getInstance().logOut();
                                }
                        })
                        .executeAsync();
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
                else {

                        callbackManager.onActivityResult(
                                requestCode,
                                resultCode,
                                data);

                        super.onActivityResult(requestCode,
                                resultCode,
                                data);
                }
        }

}
