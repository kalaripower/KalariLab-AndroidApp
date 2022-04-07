package com.example.kalarilab;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 4;
    private MainActivity mainActivity;
    public FragmentAdapter(FragmentManager fragmentManager, MainActivity mainActivity) {
        super(fragmentManager);
        this.mainActivity = mainActivity;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 1:
                return ClassesFragment.newInstance("1", "Page #2");
            case 2:
                return PremiumFragment.newInstance("2", "Page #3");

            case 3:
                return ProfileFragment.newInstance("4", "Page #5");

            default:
                return HomeFragment.newInstance("0", "Page #1");
        }
    }

}
