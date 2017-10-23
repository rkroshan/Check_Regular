package com.roshan_kumar.alternatingcurrent006.check_regular.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubContract.SubEntry;

/**
 * Created by CREATOR on 8/19/2017.
 */

public class SubDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SubjectData.db";

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ SubEntry.TABLE_NAME
            + " (" + SubEntry.SUB_NAME + " TEXT," + SubEntry.SUB_CODE
            + " TEXT PRIMARY KEY," + SubEntry.DAY_MON + " TEXT DEFAULT 'a',"
            + SubEntry.DAY_TUE + " TEXT DEFAULT 'a',"
            + SubEntry.DAY_WED + " TEXT DEFAULT 'a',"
            + SubEntry.DAY_THU + " TEXT DEFAULT 'a',"
            + SubEntry.DAY_FRI + " TEXT DEFAULT 'a',"
            + SubEntry.DAY_SAT + " TEXT DEFAULT 'a',"
            + SubEntry.DAY_SUN + " TEXT DEFAULT 'a');"
            ;
    public SubDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
