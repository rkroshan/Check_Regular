package com.roshan_kumar.alternatingcurrent006.check_regular.timetable;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roshan_kumar.alternatingcurrent006.check_regular.R;
import com.roshan_kumar.alternatingcurrent006.check_regular.adapter.StudentAdapter;
import com.roshan_kumar.alternatingcurrent006.check_regular.adapter.show_subjectData;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubContract;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubDbHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class subject_show extends Fragment {

    //decalaring variables
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    StudentAdapter studentAdapter;
    SubDbHelper subDbHelper;
    ArrayList<show_subjectData> mlist = new ArrayList<>();
    String check = "no";
    TextView title;


    public subject_show() {
        // Required empty public constructor
    }

    public subject_show(String mcheck) {
        // Required empty public constructor
        check = mcheck;
    }

    //view
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_subject_show, container, false);

            //declaring databasehelper
            subDbHelper = new SubDbHelper(getActivity());

            //setting title
            if (check.equals("summary")) {
                title = (TextView) view.findViewById(R.id.title_subjects);
                title.setText("Summary");
            } else if (check.equals("earlier_record")) {
                title = (TextView) view.findViewById(R.id.title_subjects);
                title.setText("Earlier record");
            }else if (check.equals("edit_subject")) {
                title = (TextView) view.findViewById(R.id.title_subjects);
                title.setText("Edit subject");
            }

            //decalaring stuff
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            studentAdapter = new StudentAdapter(getActivity(), loadData(), check);
            recyclerView.setAdapter(studentAdapter);
        }
        //return view
        return view;
    }

   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            // Set title
            getActivity().getActionBar()
                    .setTitle("ALL SUBJECTS");
        }
    }*/

    private ArrayList<show_subjectData> loadData() {
        Cursor cursor = query();
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                show_subjectData data = new show_subjectData();
                data.setSub_name(cursor.getString(cursor.getColumnIndexOrThrow(SubContract.SubEntry.SUB_NAME)));
                data.setSub_code(cursor.getString(cursor.getColumnIndexOrThrow(SubContract.SubEntry.SUB_CODE)));

                mlist.add(data);
                Log.e("data", data.getSub_code() + " " + data.getSub_name());
            } while (cursor.moveToNext());
        }
        return mlist;

    }

    private Cursor query() {
        SQLiteDatabase db = subDbHelper.getReadableDatabase();
        Cursor c = db.query(SubContract.SubEntry.TABLE_NAME, new String[]{SubContract.SubEntry.SUB_NAME, SubContract.SubEntry.SUB_CODE}, null, null, null, null, null);
        return c;
    }

}
