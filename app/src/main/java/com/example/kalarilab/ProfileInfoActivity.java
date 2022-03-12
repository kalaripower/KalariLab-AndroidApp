package com.example.kalarilab;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

public class ProfileInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button continueBtn;
   private androidx.appcompat.widget.Toolbar toolbar;
    private KalariLabServices kalariLabServices;
    private TextInputLayout editTextAgeParent;
    private EditText editTextAge;
    private Spinner genderSpinner;
    private ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        initHooks();
        bindings();






    }




    private void initHooks() {
        continueBtn = findViewById(R.id.continueBtn);

        toolbar = findViewById(R.id.topAppBar);
        kalariLabServices = new KalariLabServices(this);
        editTextAgeParent = findViewById(R.id.editTextAgeParent);
        editTextAge = findViewById(R.id.editTextAge);
        genderSpinner = findViewById(R.id.genderSpinner);
        arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.genders, R.layout.spinner_item);

    }

    private void bindings() {
        continueBtn.setOnClickListener(this);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        genderSpinner.setAdapter(arrayAdapter);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.back:
                        moveToMainActivity();
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
        finish();

    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continueBtn:
                saveInput();
                sendInputInfo();
                moveToMainActivity();
                break;
        }
    }

    private void sendInputInfo() {
     //   kalariLabServices.updateInfo();

    }

    private void saveInput() {
        Register.user.setAge(editTextAge.getText().toString());
        Register.user.setGender(genderSpinner.getSelectedItemPosition());
    }
    private void moveToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }









}