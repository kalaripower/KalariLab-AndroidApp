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
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditInfoActivity extends AppCompatActivity  {
    private EditText nameEntry, bioEntry;
    private TextInputLayout nameEntryParent, bioEntryParent;
    private Spinner gendersSpinner;
    private ArrayAdapter arrayAdapter ;
    private KalariLabServices kalariLabServices;
    private KalariLabUtils kalariLabUtils;
    private SessionManagement sessionManagement;
    private Button birtDateBtn;
    private DatePickerDialog datePicker;

    private String birthdate = "";
    private androidx.appcompat.widget.Toolbar toolbar;
    private Map<String, String> map = new HashMap<>();
    private   VolleyCallbackMap volleyCallbackMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        initHooks();
        bindings();

    }

    private void bindings() {

        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        gendersSpinner.setAdapter(arrayAdapter);
        gendersSpinner.setSelection(kalariLabUtils.getIndexOfGender(sessionManagement.returnUser_Gender()));
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



    private void initHooks() {
        toolbar = findViewById(R.id.topAppBar);
        volleyCallbackMap = new VolleyCallbackMap();
        nameEntry = findViewById(R.id.editTextName);
        bioEntry = findViewById(R.id.editTextBio);
        nameEntryParent = findViewById(R.id.editTextNameParent);
        bioEntryParent = findViewById(R.id.editTextBioParent);
        gendersSpinner = findViewById(R.id.genderSpinner);
        arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.genders, R.layout.spinner_item);

        sessionManagement = new SessionManagement(this);
        kalariLabServices = new KalariLabServices(this);
        kalariLabUtils = new KalariLabUtils();
        birtDateBtn = findViewById(R.id.birthDateButton);

        setText();
        initDatePicker();
        birthdate = sessionManagement.returnUser_birthDate();


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

    private void moveToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("frgToLoad", "PROFILE_FRAGMENT");
        startActivity(intent);
    }
    public void openDatePicker(View view) {
        datePicker.show();

    }
    private void setText(){
        nameEntry.setText(sessionManagement.returnUser_Name());
        bioEntry.setText(sessionManagement.returnUser_Bio());
        birtDateBtn.setText(sessionManagement.returnUser_birthDate());

    }
    private boolean infoHasChangedOrEmpty() {
        if (sessionManagement.returnUser_Name().equals("") || sessionManagement.returnUser_birthDate().equals("")
                || sessionManagement.returnUser_Bio().equals("")){
           storeInfoInSharedPreference();
            return true;
        }
       else if (nameEdited() || bioEdited() || birthDateEdited() || genderEdited()){

           return true ;
        }
       else{
            return false;
        }
    }

    public void storeInfoInSharedPreference() {

            sessionManagement.saveUser_FullName("Ahmed Al-maliki");
            sessionManagement.saveUser_Gender(kalariLabUtils.getGenderFromChar(map.get("gender")));
            sessionManagement.saveUser_Bio(map.get("bio"));
            sessionManagement.saveUser_birthDate(map.get("birth_date"));


    }

    private boolean genderEdited() {
        return gendersSpinner.getSelectedItemPosition() != kalariLabUtils.getIndexOfGender(sessionManagement.returnUser_Gender());
    }

    private boolean birthDateEdited() {
        if (birthdate.isEmpty() ){
            return false;
        }
       else  return !birthdate.equals(sessionManagement.returnUser_birthDate() );
    }

    private boolean bioEdited() {

        return !bioEntry.getText().toString()
                .equals(sessionManagement.returnUser_Bio());
    }

    private boolean nameEdited() {
        return !nameEntry.getText().toString()
                .equals(sessionManagement.returnUser_Name());
    }
    private void sendInputInfo() {
        kalariLabServices.updateInfo(new VolleyCallbacks(this, volleyCallbackMap, sessionManagement),kalariLabUtils.getGenderFromInt(gendersSpinner.getSelectedItemPosition()), birthdate, bioEntry.getText().toString());

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(infoHasChangedOrEmpty()){
            sendInputInfo();

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onStop();
        moveToMainActivity();

        }
    }
