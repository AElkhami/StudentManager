package com.elkhamitech.studentmanagerr.view.fragments.fragmentadapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.elkhamitech.studentmanagerr.view.fragments.AlarmFragment;
import com.elkhamitech.studentmanagerr.view.fragments.TimerFragment;

/**
 * Created by ElkhamiTech on 2/7/2018.
 */

public class TimeManagerAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    // tab titles
    private String[] tabTitles = new String[]{"Alarm", "Timer"};

    //Constructor to the class
    public TimeManagerAdapter(FragmentManager fm, int tabCount) {
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
                AlarmFragment tab1 = new AlarmFragment();
                return tab1;
            case 1:
                TimerFragment tab2 = new TimerFragment();
                return tab2;
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
