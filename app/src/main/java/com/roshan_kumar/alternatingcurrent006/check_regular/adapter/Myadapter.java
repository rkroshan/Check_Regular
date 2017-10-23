package com.roshan_kumar.alternatingcurrent006.check_regular.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.roshan_kumar.alternatingcurrent006.check_regular.timetable.showDailyTimeTable;


/**
 * Created by CREATOR on 9/26/2017.
 */


public class Myadapter extends FragmentStatePagerAdapter {

    public Myadapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        if(i==0)
            fragment = new showDailyTimeTable("MON",true);
        if(i==1)
            fragment = new showDailyTimeTable("TUE",true);
        if(i==2)
            fragment = new showDailyTimeTable("WED",true);
        if(i==3)
            fragment = new showDailyTimeTable("THU",true);
        if(i==4)
            fragment = new showDailyTimeTable("FRI",true);
        if(i==5)
            fragment = new showDailyTimeTable("SAT",true);
        if(i==6)
            fragment = new showDailyTimeTable("SUN",true);

        return fragment;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int i) {
        if(i==0)
            return "MONDAY";
        if(i==1)
            return "TUESDAY";
        if(i==2)
            return "WEDNESDAY";
        if(i==3)
            return "THURSADY";
        if(i==4)
            return "FRIDAY";
        if(i==5)
            return "SATURDAY";
        if(i==6)
            return "SUNDAY";
        return null;
    }
}