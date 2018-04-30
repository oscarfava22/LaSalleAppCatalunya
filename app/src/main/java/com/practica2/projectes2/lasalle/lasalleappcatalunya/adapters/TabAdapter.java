package com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TabAdapter extends FragmentPagerAdapter{

   //class to know a part of the tab
    public static class TabEntry {
        private Fragment fragment;
        private String name;

        public TabEntry(Fragment fragment, String name) {
            this.fragment = fragment;
            this.name = name;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public String getName() {
        return name;
    }
}

    private List<TabEntry> entries;

    public TabAdapter(FragmentManager fm, List<TabEntry> entries){
        super(fm);
        this.entries = entries;
    }

    @Override
    public Fragment getItem(int position) {
        return entries.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return entries.get(position).getName();
    }

}
