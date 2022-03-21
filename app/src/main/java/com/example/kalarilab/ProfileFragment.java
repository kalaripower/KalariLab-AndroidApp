package com.example.kalarilab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private KalariLabServices kalariLabServices;
    TextView fullName;
    TextView bio;
    Map<String, String> map = new HashMap<>();
    VolleyCallbackMap volleyCallbackMap;
    KalariLabUtils kalariLabUtils = new KalariLabUtils();
    SessionManagement sessionManagement ;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        kalariLabServices = new KalariLabServices(getActivity());
        volleyCallbackMap = new VolleyCallbackMap();
        sessionManagement = new SessionManagement(getActivity());

        updateInfo();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
         fullName = (TextView) view.findViewById(R.id.name);
         bio = (TextView) view.findViewById(R.id.bio);
        ImageButton settings = (ImageButton) view.findViewById(R.id.settings);
        ImageButton edit_info = (ImageButton) view.findViewById(R.id.editInfo);
        edit_info.setOnClickListener(this);
        settings.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editInfo:
                moveToEditInfoActivity();
                break;
            case R.id.settings:
                moveToSettingsActivity();

        }
    }

    private void moveToEditInfoActivity() {
        Intent intent = new Intent(getActivity(), EditInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
    private void moveToSettingsActivity() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
    private void getInfoFromDB(){
     kalariLabServices.getProfileInfo(new VolleyCallbacks(this, volleyCallbackMap ));

    }
    public void updateInfo(){

        if (infoHasChangedOrEmpty()){
            getInfoFromDB();



        }else {

            Log.d("SessionDebug", "SecondCall");

            try {
                setViewsContent();
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    public void setViewsContent() {
        fullName.setText(new StringBuilder().append(sessionManagement.returnUser_Name()).append(", ").append(kalariLabUtils.ageCalculator(sessionManagement.returnUser_birthDate())).toString());
        bio.setText(sessionManagement.returnUser_Bio());

    }

    public void storeInfoInSharedPreference() {
        sessionManagement.saveUser_Name("Ahmed Al-maliki");
        sessionManagement.saveUser_birthDate(map.get("birth_date"));
        sessionManagement.saveUser_Bio(map.get("bio"));
        sessionManagement.saveUser_Gender(kalariLabUtils.getGenderFromChar(map.get("gender")));

    }

    private boolean infoHasChangedOrEmpty() {
        if (sessionManagement.returnUser_Name().equals("") || sessionManagement.returnUser_birthDate().equals("")
        || sessionManagement.returnUser_Bio().equals("")) return true;
        else return !map.containsValue(sessionManagement.returnUser_birthDate()) && !map.containsValue(sessionManagement.returnUser_Bio());
    }
    public void setFragmentMap(){
        map.putAll(volleyCallbackMap.getMap());

    }

    @Override
    public void onResume() {
        super.onResume();
        updateInfo();
    }
}