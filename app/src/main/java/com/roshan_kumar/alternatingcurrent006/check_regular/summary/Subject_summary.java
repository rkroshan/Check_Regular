package com.roshan_kumar.alternatingcurrent006.check_regular.summary;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roshan_kumar.alternatingcurrent006.check_regular.R;
import com.roshan_kumar.alternatingcurrent006.check_regular.adapter.SummaryAdapter;
import com.roshan_kumar.alternatingcurrent006.check_regular.adapter.show_subjectsummaryData;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.Check_regularHelper;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubContract;

import java.util.ArrayList;

/**
 * Created by CREATOR on 9/26/2017.
 */

public class Subject_summary extends Fragment {
    RecyclerView recyclerview;
    SummaryAdapter summaryAdapter;
    LinearLayoutManager linearLayout;
    Check_regularHelper checkregularHelper;
    String Scode,Sname;
    ArrayList<show_subjectsummaryData> mlist = new ArrayList<>();
    TextView subname,subcode;

    public Subject_summary(){
        //default
    }

    //view
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null) {
            view = inflater.inflate(R.layout.subject_summary_recyclerview, container, false);

            //getarguments
            Scode = getArguments().getString("Scode");
            Sname = getArguments().getString("Sname");

            //pref
            SharedPreferences preferences = this.getActivity().getSharedPreferences("database_version", 0);

            //decalaring stuff
            subcode = (TextView) view.findViewById(R.id.Subcode);
            subname = (TextView) view.findViewById(R.id.Subname);
            checkregularHelper = new Check_regularHelper(getActivity(),Scode);
            recyclerview = (RecyclerView) view.findViewById(R.id.summaryRecylcerview);
            linearLayout = new LinearLayoutManager(getActivity());
            recyclerview.setHasFixedSize(true);
            recyclerview.setLayoutManager(linearLayout);
            summaryAdapter = new SummaryAdapter(getActivity(), loadData());
            recyclerview.setAdapter(summaryAdapter);

            //set textview
            subcode.setText(Scode);
            subname.setText(Sname);
        }

        return view;
    }


    private ArrayList<show_subjectsummaryData> loadData() {
        Cursor cursor = query();
        if(cursor!=null&&cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                int i = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(SubContract.Check_Regular.PRESENT)));
                int j =  Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(SubContract.Check_Regular.ABSENT)));

                show_subjectsummaryData data = new show_subjectsummaryData();
                data.setDate(cursor.getString(cursor.getColumnIndexOrThrow(SubContract.Check_Regular.DATE)));
                if(j>0)
                data.setStatus("Present("+i+")"+",Absent("+j+")");
                else
                    data.setStatus("Present ("+i+")");

                mlist.add(data);
                Log.e("data" , data.getDate() + " " + data.getStatus());
            }while(cursor.moveToNext());
        }
        return mlist;

    }

    private Cursor query(){
        SQLiteDatabase db = checkregularHelper.getReadableDatabase();
        Cursor c = db.query(Scode,new String[]{SubContract.Check_Regular.DATE, SubContract.Check_Regular.PRESENT, SubContract.Check_Regular.ABSENT},null,null,null,null,null);
        return c;
    }

}
