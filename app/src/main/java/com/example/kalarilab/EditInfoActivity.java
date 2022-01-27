package com.example.kalarilab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class EditInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText nameEntry, bioEntry, ageEntry;
    private TextInputLayout nameEntryParent, bioEntryParent, ageEntryParent;
    private Spinner gendersSpinner;
    private ImageButton backBtn;
    private ArrayAdapter arrayAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        initHooks();
        bindings();
    }

    private void bindings() {
        backBtn.setOnClickListener(this);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        gendersSpinner.setAdapter(arrayAdapter);
    }
    private void setText(){


    }

    private void initHooks() {
        nameEntry = findViewById(R.id.editTextName);
        bioEntry = findViewById(R.id.editTextBio);
        ageEntry = findViewById(R.id.editAge);
        nameEntryParent = findViewById(R.id.editTextNameParent);
        bioEntryParent = findViewById(R.id.editTextBioParent);
        ageEntryParent = findViewById(R.id.editTextAgeParent);
        gendersSpinner = findViewById(R.id.genderSpinner);
        backBtn = findViewById(R.id.backButton);
        arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.genders, R.layout.spinner_item);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton:
                setText();
                moveToMainActivity();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        setText();
        super.onBackPressed();

    }

    private void moveToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}