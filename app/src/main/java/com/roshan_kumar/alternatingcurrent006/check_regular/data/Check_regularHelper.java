package com.roshan_kumar.alternatingcurrent006.check_regular.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by CREATOR on 8/22/2017.
 */

public class Check_regularHelper extends SQLiteOpenHelper {

    Context mcontext=null;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Check_regular.db";
    String Scode;
    public String s;


    public Check_regularHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mcontext=context;
    }

    public Check_regularHelper(Context context,String mScode){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mcontext=context;
        this.Scode=mScode;
        Log.e("Scodec-----------------",this.Scode);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i("Scode-----------------",this.Scode);

         String SQL_CREATE_ENTRIES = "CREATE TABLE "+ this.Scode
                + " (" + SubContract.Check_Regular.DATE + " TEXT PRIMARY KEY, " + SubContract.Check_Regular.PRESENT + " INTEGER DEFAULT 0, " +
                 SubContract.Check_Regular.ABSENT + " INTEGER DEFAULT 0, " +SubContract.Check_Regular.TOTAL + " TEXT DEFAULT '0')";
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}


