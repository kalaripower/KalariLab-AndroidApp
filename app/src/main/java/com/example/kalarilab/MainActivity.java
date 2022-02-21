package com.example.kalarilab;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    // private GoogleSignInClient mGoogleSignInClient;
    // private SessionManagement sessionManagement;


    private ViewPager viewPager;
    private FragmentsAdapter fragmentsAdapter;
    private static final int NUM_PAGES = 4;

    private BottomNavigationView bottomNavigationView;
    private ColorStateList navigationViewColorStateList;
    private Fragment  homeFragment, profileFragment, premiumFragment, shopFragment, classesFragment;
    private RelativeLayout relativeLayout;
    SwipeListener swipeListener;
    private FrameLayout frameLayout;
    Fragment[] fragments = new Fragment[5];

    // FOR NAVIGATION VIEW ITEM ICON COLOR
    int[][] states = new int[][]{
            new int[]{-android.R.attr.state_checked},  // unchecked
            new int[]{android.R.attr.state_checked},   // checked
            new int[]{}                                // default
    };
    // Fill in color corresponding to state defined in state
    int[] colors = new int[]{
            Color.parseColor("#aaa8a8"),
            Color.parseColor("#ce262f"),
            Color.parseColor("#aaa8a8"),
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initHooks();
        bindings();
        fillFragmentsList();
        runFragment(homeFragment, false, 'n');
        // configureGoogleRequest();


    }

    private void fillFragmentsList() {
        fragments[0] = homeFragment;
        fragments[1] = classesFragment;
        fragments[2] = premiumFragment;
        fragments[3] = shopFragment;
        fragments[4] = profileFragment;


    }


    private void initHooks() {

        //sessionManagement = new SessionManagement(MainActivity.this);

        frameLayout = findViewById(R.id.container);
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navigationViewColorStateList = new ColorStateList(states, colors);
        relativeLayout = findViewById(R.id.relativeLayoutMainActivity);
        premiumFragment = new PremiumFragment();
        shopFragment = new ShopFragment();
        swipeListener = new SwipeListener(relativeLayout);
        classesFragment = new ClassesFragment();

    }
    private void bindings() {
        bottomNavigationView.setItemIconTintList(navigationViewColorStateList);



        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_page:

                    runFragment(homeFragment, false, 'n');

                    break;
                case R.id.profile_page:

                    runFragment(profileFragment, false, 'n');
                    break;
                case R.id.premium_page:

                    runFragment(premiumFragment, false, 'n');
                    break;
                case R.id.shop_page:

                    runFragment(shopFragment, false, 'n');
                    break;
                case R.id.classes_page:
                    runFragment(classesFragment, false, 'n');
            }

            return true;
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //case R.id.signOutBtn:
            //signOut();
            //  break;
//            case R.id.posturesCard:
//                goToPosturesActivity();
//
//                break;

        }
    }






//    private void configureGoogleRequest(){
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//    }

//    private void moveToVideoRecorderActivity() {
//        Intent intent = new Intent(MainActivity.this, VideoRecorder.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        startActivity(intent);
//        finish();
//
//    }

//    private void signOut() {
//        Log.d("DebugLogout", "0 started" + sessionManagement);
//
//        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()) {
//                    Log.d("DebugLogout", "1" + sessionManagement.returnSession());
//                    sessionManagement.removeSession();
//                    Log.d("DebugLogout", "2" + sessionManagement.returnSession());
//
//                    startActivity(new Intent(MainActivity.this, LogIn.class));
//                    sendBroadcastToPreventAccessToAllActivities();
//                    finish();
//                }
//                else {
//                    Log.d("DebugLogout", "3  Failed" + sessionManagement.returnSession());
//
//                }
//
//            }
//
//        });
//    }

//    private void sendBroadcastToPreventAccessToAllActivities() {
//        Intent broadcastIntent = new Intent();
//        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
//        sendBroadcast(broadcastIntent);
//    }

    private void runFragment(Fragment fragment,boolean swiped,  char direction) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (getVisibleFragment() != fragment && fragment != null) {
            if (swiped) {
                if (direction == 'l') {

                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                            R.anim.slide_in_left, R.anim.slide_out_right);

                } else{
                    transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,
                            R.anim.slide_in_right, R.anim.slide_out_left);

                }
            }
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }




        }
    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }


    private int getFragmentPosition(Fragment fragment) {
        for (int i = 0 ; i < fragments.length ; i ++){
            if (fragments[i] == fragment){

                return  i;
            }
        }
       return 0;
    }


    private void updateNavigationBarState(int currentItem){
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.getItem(currentItem);
        item.setChecked(true);


    }

    private class SwipeListener implements View.OnTouchListener {
        GestureDetector gestureDetector;

        public SwipeListener(View view) {
            int threshold = 100;
            int velocity_threshold = 100;
            GestureDetector.SimpleOnGestureListener listener =
                    new GestureDetector.SimpleOnGestureListener(){
                        @Override
                        public boolean onDown(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                            float xDiff = e2.getX() - e1.getX();
                            float yDiff = e2.getY() - e1.getY();
                            try {
                                if (Math.abs(xDiff) > Math.abs(yDiff)){
                                    if (Math.abs(xDiff) > threshold && Math.abs(velocityX) > velocity_threshold){
                                        if (xDiff > 0){
                                            swipeRight();

                                        }else {
                                            swipeLeft();



                                        }
                                        return true;
                                    }

                                }else {
                                    if (Math.abs(yDiff) > threshold && Math.abs(velocityY) > velocity_threshold){
                                        if (yDiff > 0){
                                            //swipeDown
                                        }

                                    }
                                    return true;
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            return false;
                        }
                    };
            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
    }

    private void swipeLeft() {
        runFragment(fragments[getFragmentPosition(getVisibleFragment()) + 1], true ,'l');
        updateNavigationBarState(getFragmentPosition(getVisibleFragment()) + 1);

    }

    private void swipeRight() {
        runFragment(fragments[getFragmentPosition(getVisibleFragment()) - 1], true, 'r');
        updateNavigationBarState(getFragmentPosition(getVisibleFragment()) - 1);

    }





}