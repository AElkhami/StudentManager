package com.elkhamitech.studentmanagerr.view.activities;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.view.fragments.fragmentadapters.SubjectMarksAdapter;

import java.util.Objects;

public class SubjectMarksMainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    //This is our tabLayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    private SubjectMarksAdapter marksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_marks_main);

        //Adding toolbar to the activity
        Toolbar toolbar = findViewById(R.id.toolbar_subjectmarks);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Subject Marks");

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //Initializing the tablayout
        tabLayout = findViewById(R.id.tabLayout_subjectmarks);

        marksAdapter = new SubjectMarksAdapter(getSupportFragmentManager(),4);

        int tabsCount = marksAdapter.getCount();

        for(int i = 0 ; i < tabsCount ; i ++){
            //Adding the tabs using addTab() method
            tabLayout.addTab(tabLayout.newTab());
        }


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = findViewById(R.id.pager_subjectmarks);

        //Creating our pager adapter
        SubjectMarksAdapter adapter = new SubjectMarksAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

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

