package com.example.submission5;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.submission5.fragment.FragmentMovie;
import com.example.submission5.fragment.FragmentTvShow;

public class PagerAdapter extends FragmentPagerAdapter {
    private Context mcontext;
    public PagerAdapter(Context context, FragmentManager fm){
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mcontext = context;

    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FragmentMovie();
                break;
            case 1:
                fragment = new FragmentTvShow();
                break;
        }
        return fragment;
    }

    /* @Nullable
     @Override
     public CharSequence getPageTitle(int position){
         return titleList.get(position);
     }

     @Override
     public int getCount() {
         return titleList.size();
     }
     public void addTitle(int title){
         titleList.add(Integer.toString(title));
     }*/
    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.text1,
            R.string.text2
    };

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mcontext.getResources().getString(TAB_TITLES[position]);
    }
}
