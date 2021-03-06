package com.example.kalarilab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileNameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileNameFragment extends Fragment  {
    EditText firstNameEntry;
    EditText lastNameEntry;
    TextInputLayout firstNameEntryParent;
    TextInputLayout lastNameEntryParent;
    TextView firstNameWarning;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileNameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileNameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileNameFragment newInstance(String param1, String param2) {
        ProfileNameFragment fragment = new ProfileNameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_name, container, false);
        initHooks(view);
        bindings();

        return view;
    }

    private void bindings() {
        firstNameWarning.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    firstNameWarning.setText("");
                }
            }
        });
    }

    private void initHooks(View view) {
         firstNameEntry = (EditText) view.findViewById(R.id.editTextFirstName);
         lastNameEntry  = (EditText) view.findViewById(R.id.editTextFLastName);
         firstNameEntryParent = (TextInputLayout) view.findViewById(R.id.editTextFirstNameParent);
         lastNameEntryParent = (TextInputLayout)  view.findViewById(R.id.editTextLastNameParent);
        firstNameWarning = (TextView) view.findViewById(R.id.warningTextFirstName);
    }

    @Override
    public void onStop() {
        super.onStop();

        Register.user.setFirstName(firstNameEntry.getText().toString());
        Register.user.setLastName(lastNameEntry.getText().toString());

    }


}