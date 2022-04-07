package com.example.kalarilab;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.progressindicator.CircularProgressIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressTrackingSystem progressTrackingSystem;
    private Streak streak;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        postponeEnterTransition();
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        progressTrackingSystem = new ProgressTrackingSystem();
        streak = new Streak(progressTrackingSystem, getActivity());
        TextView classesProgressText = (TextView) view.findViewById(R.id.classesProgressText);
        TextView levelsProgressText = (TextView) view.findViewById(R.id.levelsProgressText);
        TextView totalPoints = (TextView) view.findViewById(R.id.totalPoints);
        TextView weeklyPoints = (TextView) view.findViewById(R.id.weeklyPoints);

        CircularProgressIndicator circularProgressIndicatorClasses =(CircularProgressIndicator) view.findViewById(R.id.progressCircleClasses);
        CircularProgressIndicator  circularProgressIndicatorPoints = (CircularProgressIndicator) view.findViewById(R.id.progressCirclePoints);
        totalPoints.setText(String.valueOf(progressTrackingSystem.getTotalPoints()));
        weeklyPoints.setText(String.valueOf(progressTrackingSystem.getWeeklyPoints()));
        circularProgressIndicatorPoints.setProgress(streak.getWeekProgress());
        classesProgressText.setText(new StringBuilder().append(getClassReached()).append("/").append(getNumOfClasses()).toString());
        levelsProgressText.setText(new StringBuilder().append("Level ").append(getLevelReached()).toString());

        circularProgressIndicatorClasses.setMax(7);
        circularProgressIndicatorClasses.setMax(getNumOfClasses());
        circularProgressIndicatorClasses.setProgress(getClassReached());

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);

        CardView posturesCard = (CardView) view.findViewById(R.id.posturesCard);
        posturesCard.setOnClickListener(this);
        startPostponedEnterTransition();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              refreshPage();
              swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private void refreshPage() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //case R.id.signOutBtn:
            //signOut();
            //  break;
            case R.id.posturesCard:
                goToPosturesActivity();

                break;

        }

    }
    private int getLevelReached(){
        //Gets reached level from DB
        return 7;
    }
    private int getClassReached(){
        //Gets reached class from cloud
        return 11;
    }
    private int getNumOfClasses() {
        //Gets number of classes from cloud
        return 22;
    }

    private void goToPosturesActivity() {
        Intent intent = new Intent(getActivity(), PosturesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

    }

}


