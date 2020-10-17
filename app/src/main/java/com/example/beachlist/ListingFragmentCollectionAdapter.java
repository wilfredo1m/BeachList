package com.example.beachlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListingFragmentCollectionAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList= new ArrayList<>();
    private  List<String> fragmentNames = new ArrayList<>();

    public ListingFragmentCollectionAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);


    }

    @NonNull
    @Override
    public Fragment getItem(int position)  {
        return fragmentList.get(position);
    }

    @Override
    public int getCount()  {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String name){
        fragmentList.add(fragment);
        fragmentNames.add(name);
    }

}
