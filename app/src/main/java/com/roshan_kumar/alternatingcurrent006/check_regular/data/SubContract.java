package com.roshan_kumar.alternatingcurrent006.check_regular.data;

import android.provider.BaseColumns;

/**
 * Created by CREATOR on 8/19/2017.
 */

public final class SubContract {

    public SubContract(){}

    public static final class SubEntry implements BaseColumns{
        public static final String TABLE_NAME = "TimeTable";
        public static final String SUB_NAME = "sub_name";
        public static final String SUB_CODE = "sub_code";
        public static final String DAY_MON = "MON";
        public static final String DAY_TUE = "TUE";
        public static final String DAY_WED = "WED";
        public static final String DAY_THU = "THU";
        public static final String DAY_FRI = "FRI";
        public static final String DAY_SAT = "SAT";
        public static final String DAY_SUN = "SUN";

    }

    public static final class Check_Regular{/*
        public static final String TABLE_NAME = "Check_Regular";*/
        public static final String DATE = "Date";
        public static final String PRESENT="present";
        public static final String TOTAL="total";
        public static final String ABSENT="absent";
    }
}
