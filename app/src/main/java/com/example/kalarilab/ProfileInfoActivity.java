package com.example.kalarilab;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class ProfileInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button continueBtn;
   private androidx.appcompat.widget.Toolbar toolbar;
    private KalariLabServices kalariLabServices;
    private Button birtDateBtn;
    private Spinner genderSpinner;
    private ArrayAdapter arrayAdapter;
    private DatePickerDialog datePicker;
    private String birthdate = "";
    private KalariLabUtils kalariLabUtils;


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
        birtDateBtn = findViewById(R.id.birthDateButton);
        genderSpinner = findViewById(R.id.genderSpinner);
        kalariLabUtils = new KalariLabUtils();
        arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.genders, R.layout.spinner_item);
        initDatePicker();


    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                birthdate = makeDateToString(year, month, dayOfMonth);
                birtDateBtn.setText(birthdate);


            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePicker = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    private String makeDateToString(int year, int month, int dayOfMonth) {
        return year+"-"+month+"-"+dayOfMonth;
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
        kalariLabServices.updateInfo(kalariLabUtils.getGenderFromInt(Register.user.getGender()), birthdate, "Ahmed Almaliki");

    }



    private void saveInput() {
        Register.user.setAge(birthdate);
        Register.user.setGender(genderSpinner.getSelectedItemPosition());
    }
    private void moveToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }


    public void openDatePicker(View view) {
        datePicker.show();

    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

}