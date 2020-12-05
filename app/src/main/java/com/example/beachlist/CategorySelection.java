package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class CategorySelection extends AppCompatActivity {
    Button backBtn;
    private TabLayout tabLayout;
    private ViewPager viewpager;
    int tabPosition;
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);                                              //set view to activity_category_page xml

        //initializing tabs and pager
        tabLayout = findViewById(R.id.category_tab_layout);                                           //tab layout to be used with viewpager
        viewpager = findViewById(R.id.category_type_pager);                                           //view pager in xml
        toolbar = findViewById(R.id.category_toolbar);                                                //toolbar in xml
        final CategoryPagerAdapter pagerAdapter= new                                                  //set up pagerAdapter with the created CategoryPage Adapter
                CategoryPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());            //supportfragment manager to be able to cycle through fragments
        tabLayout.setupWithViewPager(viewpager);                                                      //setup tablayout with pager to swipe between tabs
        viewpager.setAdapter(pagerAdapter);                                                           //set viewpaer with the adapter to be able to swipe through screens with the pager


        backBtn = findViewById(R.id.back_btn_from_category);                                          //link button to xml
        backBtn.setOnClickListener(new View.OnClickListener() {                                       //set on click listener for button
            @Override
            public void onClick(View v) {
                openHomeScreen();                                                                     //open home screen when button is clicked
            }
        });

//******************TAB POSITION BASED ON CALL FROM ANOTHER PAGE**********************************//
        //set int to the returned value from sold listing/ active listing tab
        tabPosition = checkTabCall();
        if(tabPosition==1)
        {
            viewpager.setCurrentItem(0);
        }
        else if (tabPosition ==2)
        {
            viewpager.setCurrentItem(1);
        }
//******************END TAB POSITION BASED ON CALL FROM ANOTHER PAGE*****************************//

//*******************LISTENER FOR TAB CALL*****************************************************//
        //change tab view when user selects a tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            viewpager.setCurrentItem(tab.getPosition());                                            //set position of view pager to the tab position
            toolbar.setTitle(pagerAdapter.getPageTitle(tab.getPosition()));                         //set title of toolbar to the title associated with position in adapter
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//*******************END LISTENER FOR TAB CALL*****************************************************//



    }//end onCreate()


    //gets which tab to go to based on back button call
    // 1 Item Category
    // 2 Service Category
    public int checkTabCall()
    {
        //get intent for tab call
        tabPosition= getIntent().getIntExtra("tabPosition",7);                    //get tab position
        return tabPosition;

    }

    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);              //send user back to home screen
        startActivity(openScreen);
    }

}
