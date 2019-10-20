package com.elkhamitech.studentmanagerr.view.fragments.fragmentadapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.elkhamitech.studentmanagerr.view.fragments.FinalExamFragment;
import com.elkhamitech.studentmanagerr.view.fragments.MidTermFragment;
import com.elkhamitech.studentmanagerr.view.fragments.OralFragment;
import com.elkhamitech.studentmanagerr.view.fragments.QuizFragment;


public class SubjectMarksAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    private int tabCount;

    // tab titles
    private String[] tabTitles = new String[]{"Oral", "Quiz","MidTerm","Final Exam"};

    //Constructor to the class
    public SubjectMarksAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    // overriding getPageTitle()
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                OralFragment tab1 = new OralFragment();
                return tab1;
            case 1:
                QuizFragment tab2 = new QuizFragment();
                return tab2;
            case 2:
                MidTermFragment tab3 = new MidTermFragment();
                return tab3;
            case 3:
                FinalExamFragment tab4 = new FinalExamFragment();
                return tab4;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }

}
