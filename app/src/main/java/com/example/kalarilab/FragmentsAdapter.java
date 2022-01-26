package com.example.kalarilab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

public class FragmentsAdapter extends   PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public FragmentsAdapter(Context context) {
        this.context = context;
    }

    private Fragment[] fragments = {
            new HomeFragment(),
            new PremiumFragment(),
            new ShopFragment(),
            new ProfileFragment()
    };


    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragments_screen_slide_page, container, false);

        container.addView(view);
        return view;
    }





    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ScrollView)object);
    }
}
