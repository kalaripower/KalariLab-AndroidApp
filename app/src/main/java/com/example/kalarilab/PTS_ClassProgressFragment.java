package com.example.kalarilab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PTS_ClassProgressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PTS_ClassProgressFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressTrackingSystem progressTrackingSystem;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PTS_ClassProgressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PTS_ClassProgress.
     */
    // TODO: Rename and change types and number of parameters
    public static PTS_ClassProgressFragment newInstance(String param1, String param2) {
        PTS_ClassProgressFragment fragment = new PTS_ClassProgressFragment();
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
        View view =  inflater.inflate(R.layout.fragment_p_t_s__class_progress, container, false);
        progressTrackingSystem = new ProgressTrackingSystem(getLevelReached(), getClassReached(), getNumOfClasses());
        CircularProgressIndicator progressIndicator = (CircularProgressIndicator) view.findViewById(R.id.progressCircleDeterminate);
        TextView classesProgressText = (TextView) view.findViewById(R.id.classesProgressText);
        TextView levelsProgressText = (TextView) view.findViewById(R.id.levelsProgressText);
        classesProgressText.setText(new StringBuilder().append(getClassReached()).append("/").append(getNumOfClasses()).toString());
        levelsProgressText.setText(new StringBuilder().append("Level ").append(getLevelReached()).toString());
        progressIndicator.setMax(getNumOfClasses());
        progressIndicator.setProgress(getClassReached());


        return view;

    }
    //For testing
    private int getLevelReached(){
        //Gets reached level from DB
        return 7;
    }
    private int getClassReached(){
        //Gets reached class from cloud
        return 11;
    }
    private int getNumOfClasses(){
        //Gets number of classes from cloud
        return 22;
    }
}