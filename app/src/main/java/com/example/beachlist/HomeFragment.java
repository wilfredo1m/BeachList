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

public class HomeFragment extends Fragment {
    View view;
    ViewPager viewPager;
    TabLayout tabLayout;
    Button categoryBtn;

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



//********************************BUTTON GROUP*************************************************************//
        categoryBtn = view.findViewById(R.id.btn_categories);
        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openScreen = new Intent(getActivity(), CategorySelection.class);
                startActivity(openScreen);
            }
        });

        return view;
    }
//***************************END BUTTON GROUP************************************************************//
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).select();

        //*************************CALL FROM HOMEAFTERLOGIN TO SEE WHICH TAB TO GO TO AFTER BACKBUTTON PRESS IN A SELECTED PAGE***************************//
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
        //*************************END TAB SELECTION AFTER BACK BUTTON PRESS***************************************************************************//


        //listener to see which tab is selected
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

    //pager will call the hometab adapter in order to switch between tab based on which was selected
    private void setUpViewPager(ViewPager viewPager) {
        //creating a new tab adapter object with a child fragment manager for the 3 tabs that are fragment objects
        HomeTabsAdapter adapter = new HomeTabsAdapter(getChildFragmentManager());
        //fragments added to the adapter to swap between
        adapter.addFragment(new ItemHomeSearchTab(), "Items");
        adapter.addFragment(new UserHomeSearchTab(), "Users");
        adapter.addFragment(new ServiceHomeSearchTab(), "Services");
        //set the adapter to the view pager
        viewPager.setAdapter(adapter);
    }
}