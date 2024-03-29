package com.elkhamitech.studentmanagerr.view.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.elkhamitech.studentmanagerr.view.fragments.fragmentadapters.TimeManagerAdapter;
import com.elkhamitech.studentmanagerr.R;

public class MainTimeManagerActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {


    //This is our tabLayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_time_manager);


        //Adding toolbar to the activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Time Manager");

        //Initializing the tablayout
        tabLayout = findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = findViewById(R.id.pager);

        //Creating our pager adapter
        TimeManagerAdapter adapter = new TimeManagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);


        //Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(this);

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}