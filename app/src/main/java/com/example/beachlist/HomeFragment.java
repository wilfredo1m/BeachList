package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    View view;
    ViewPager viewPager;
    TabLayout tabLayout;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.home_menu_tab_view);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        // Open the correct tab based on which screen we came from
        int tab = getActivity().getIntent().getIntExtra("tab",8);
        // Users
        if(tab == 1) {
            tabLayout.getTabAt(1).select();
        }
        // Services
        else if(tab == 2){
            tabLayout.getTabAt(2).select();
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        HomeTabsAdapter adapter = new HomeTabsAdapter(getChildFragmentManager());

        adapter.addFragment(new ItemHomeSearchTab(), "Items");
        adapter.addFragment(new UserHomeSearchTab(), "Users");
        adapter.addFragment(new ServiceHomeSearchTab(), "Services");

        viewPager.setAdapter(adapter);
    }
}