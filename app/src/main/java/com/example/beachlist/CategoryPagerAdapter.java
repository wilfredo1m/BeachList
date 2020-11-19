package com.example.beachlist;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CategoryPagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    public CategoryPagerAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new ItemsCategory();                       //open ItemCategory page
            case 1:
                return new ServicesCategory();                   //open service category page
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Items Category";                      //return item title of the page based on position
            case 1:
                return "Services Category";                  //return Service title of the page based on position
        }
        return null;
    }
}

