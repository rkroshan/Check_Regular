package com.roshan_kumar.alternatingcurrent006.check_regular.timetable;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roshan_kumar.alternatingcurrent006.check_regular.R;
import com.roshan_kumar.alternatingcurrent006.check_regular.TimetableSharing.ShareTimeTable;
import com.roshan_kumar.alternatingcurrent006.check_regular.activities.MainActivity;
import com.roshan_kumar.alternatingcurrent006.check_regular.adapter.StudentAdapter;
import com.roshan_kumar.alternatingcurrent006.check_regular.adapter.show_subjectData;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubContract;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubDbHelper;

import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class showDailyTimeTable extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    StudentAdapter studentAdapter1;
    SubDbHelper subDbhelper;
    ArrayList<show_subjectData> mlist = new ArrayList<>();
    String day = null;
    TextView title;
    Boolean timetable = false;


    public showDailyTimeTable() {
        // Required empty public constructor
    }

    public showDailyTimeTable(String mday, Boolean mtimetable) {
        this.day = mday;
        this.timetable = mtimetable;
    }

    //View
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (day == null) {
            MainActivity.isMainActivityShown = true;
        }
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_subject_show, container, false);

            //alert
            SharedPreferences preferences = getActivity().getSharedPreferences("share", MODE_PRIVATE);
            if (!preferences.getBoolean("share", false)) {

                ShareTimeTable shareTimeTable = new ShareTimeTable();
                MainActivity.isMainActivityShown = true;
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                fragmentTransaction1.replace(R.id.content_main, shareTimeTable)
                        .addToBackStack("share_timeTable")
                        .commit();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("share", true);
                editor.apply();
            }

            //dec database
            subDbhelper = new SubDbHelper(getActivity());

            Date dt = new Date();
            String[] date = dt.toString().split(" ");

            title = (TextView) view.findViewById(R.id.title_subjects);
            if (!timetable) {
                title.setText(date[0]);
            } else title.setVisibility(View.GONE);
                /*
        try{
            day=getArguments().getString("day");
        }catch (Exception e){}*/

            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            layoutManager = new LinearLayoutManager(getActivity());
            if (day == null) {
                studentAdapter1 = new StudentAdapter(getActivity(), loadData(date[0]), true);
            } else {
                studentAdapter1 = new StudentAdapter(getActivity(), loadData(day),true,true);
            }
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(studentAdapter1);
        }
        //return view
        return view;
    }

/*    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            // Set title
            getActivity().getActionBar()
                    .setTitle("Today's TimeTable");
        }
    }*/

    private ArrayList<show_subjectData> loadData(String s) {
        Cursor cursor = query(s);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                show_subjectData data = new show_subjectData();
                data.setSub_name(cursor.getString(cursor.getColumnIndexOrThrow(SubContract.SubEntry.SUB_NAME)));
                data.setSub_code(cursor.getString(cursor.getColumnIndexOrThrow(SubContract.SubEntry.SUB_CODE)));
                String string = cursor.getString(cursor.getColumnIndexOrThrow(s.toUpperCase()));
                if(!string.equals("1")){
                    data.settime(string);
                }else {
                    data.settime("--:--");
                }
                mlist.add(data);
                //Log.e("data" , data.getSub_code() + " " + data.getSub_name());
            } while (cursor.moveToNext());
        }
        return mlist;

    }

    private Cursor query(String s) {
        s = s.toUpperCase();
        SQLiteDatabase db = subDbhelper.getReadableDatabase();
        String[] args = {"a"};
        Cursor c = db.query(SubContract.SubEntry.TABLE_NAME, new String[]{SubContract.SubEntry.SUB_CODE, SubContract.SubEntry.SUB_NAME, s}, s + "!=?", args, null, null, s);
        return c;
    }


}
