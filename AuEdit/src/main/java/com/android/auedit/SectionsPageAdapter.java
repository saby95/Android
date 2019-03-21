package com.android.auedit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shabarik on 10/16/2017.
 */

class SectionsPageAdapter extends FragmentPagerAdapter{

    private final List<Fragment> listFragment = new ArrayList<>();
    private final List<String> PageTitles = new ArrayList<>();

    public void addFragment(Fragment frag, String title){
        listFragment.add(frag);
        PageTitles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PageTitles.get(position);
    }

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }


    @Override
    public int getCount() {
        return listFragment.size();
    }
}
