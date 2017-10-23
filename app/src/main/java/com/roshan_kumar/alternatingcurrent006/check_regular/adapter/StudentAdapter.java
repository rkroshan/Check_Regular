package com.roshan_kumar.alternatingcurrent006.check_regular.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roshan_kumar.alternatingcurrent006.check_regular.activities.MainActivity;
import com.roshan_kumar.alternatingcurrent006.check_regular.R;
import com.roshan_kumar.alternatingcurrent006.check_regular.activities.filling_data.subject_edit;
import com.roshan_kumar.alternatingcurrent006.check_regular.summary.Summary;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.Check_regularHelper;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubContract;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubDbHelper;
import com.roshan_kumar.alternatingcurrent006.check_regular.timetable.Check_regularSubject;
import com.roshan_kumar.alternatingcurrent006.check_regular.timetable.EarlierRecord;
import com.roshan_kumar.alternatingcurrent006.check_regular.timetable.showDailyTimeTable;

import java.util.ArrayList;

/**
 * Created by CREATOR on 8/20/2017.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    Context mcontext;
    ArrayList<show_subjectData> mlist;
    boolean show_dailyTimeTable=false;
    String subject_dataSummary="false";
    Boolean isTimetable=false;

    public StudentAdapter (Context context, ArrayList<show_subjectData> list){
        mcontext = context;
        mlist=list;
    }
    public StudentAdapter (Context context, ArrayList<show_subjectData> list, Boolean check){
        mcontext = context;
        mlist=list;
        show_dailyTimeTable=check;
    }
    public StudentAdapter (Context context, ArrayList<show_subjectData> list, Boolean check,boolean Timetable){
        mcontext = context;
        mlist=list;
        show_dailyTimeTable=check;
        isTimetable=Timetable;
    }
    public StudentAdapter(Context context, ArrayList<show_subjectData> list, String check){
        mcontext = context;
        mlist=list;
        subject_dataSummary=check;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(show_dailyTimeTable){
            view = LayoutInflater.from(mcontext).inflate(R.layout.linearlayout_student_daily, parent, false);
        }else {
            view = LayoutInflater.from(mcontext).inflate(R.layout.linearlayout_subject_show, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        show_subjectData element = mlist.get(position);
        holder.show_code.setText(element.getSub_code());
        Log.e("sub_code",element.getSub_code());
        holder.show_subject.setText(element.getSub_name());
        if(show_dailyTimeTable){
            holder.time.setText(element.gettime());
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView show_code,show_subject,time;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            show_code = (TextView) itemView.findViewById(R.id.show_code);
            show_subject = (TextView) itemView.findViewById(R.id.show_subject);
            if(show_dailyTimeTable){
                time = (TextView) itemView.findViewById(R.id.time1);
            }
        }

        @Override
        public void onClick(View v) {
            switchFragment();
        }

        private void switchFragment() {
            if (show_dailyTimeTable && !isTimetable) {
                Check_regularSubject checkRegularSubject = new Check_regularSubject();
                MainActivity.isMainActivityShown=false;
                Bundle args = new Bundle();
                args.putString("subjectCode", "_"+mlist.get(getAdapterPosition()).getSub_code());
                checkRegularSubject.setArguments(args);

                FragmentManager fragmentManager = ((FragmentActivity) mcontext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, checkRegularSubject)
                        .addToBackStack("Check_regularSubject")
                        .commit();
            }
            else if(subject_dataSummary.equals("edit_subject")){
                subject_edit subjectEdit = new subject_edit();
                MainActivity.isMainActivityShown=false;
                Bundle args = new Bundle();
                args.putString("subjectCode", mlist.get(getAdapterPosition()).getSub_code());
                subjectEdit.setArguments(args);

                FragmentManager fragmentManager = ((FragmentActivity) mcontext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, subjectEdit)
                        .addToBackStack("subjectEdit")
                        .commit();
            }
            else if(subject_dataSummary.equals("summary")){
                Summary summary = new Summary();
                MainActivity.isMainActivityShown=false;
                Bundle args = new Bundle();
                args.putString("subjectCode", "_"+mlist.get(getAdapterPosition()).getSub_code());
                args.putString("subjectName",mlist.get(getAdapterPosition()).getSub_name());
                summary.setArguments(args);

                FragmentManager fragmentManager = ((FragmentActivity) mcontext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, summary)
                        .addToBackStack("summary")
                        .commit();
            }
            else if (subject_dataSummary.equals("all_subject")){
                Check_regularSubject checkRegularSubject = new Check_regularSubject();
                MainActivity.isMainActivityShown=false;
                Bundle args = new Bundle();
                args.putString("subjectCode", "_"+mlist.get(getAdapterPosition()).getSub_code());
                checkRegularSubject.setArguments(args);

                FragmentManager fragmentManager = ((FragmentActivity) mcontext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, checkRegularSubject)
                        .addToBackStack("Check_regularSubject")
                        .commit();
            }
            else if (subject_dataSummary.equals("earlier_record")){
                EarlierRecord earlierRecord = new EarlierRecord();
                MainActivity.isMainActivityShown=false;
                Bundle args = new Bundle();
                args.putString("subjectCode",mlist.get(getAdapterPosition()).getSub_code());
                earlierRecord.setArguments(args);

                FragmentManager fragmentManager = ((FragmentActivity) mcontext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, earlierRecord)
                        .addToBackStack("earlierRecord")
                        .commit();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(subject_dataSummary.equals("all_subject")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setMessage("Are you sure you wanna delete " + mlist.get(getAdapterPosition()).getSub_name() + " subject  ??")
                        .setPositiveButton("Yes Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String[] args = {mlist.get(getAdapterPosition()).getSub_code()};
                                SubDbHelper subDbHelper = new SubDbHelper(mcontext);
                                SQLiteDatabase db = subDbHelper.getWritableDatabase();
                                db.delete(SubContract.SubEntry.TABLE_NAME, SubContract.SubEntry.SUB_CODE + "=?", args);
                                db.close();

                                Check_regularHelper checkRegularHelper = new Check_regularHelper(mcontext);
                                db = checkRegularHelper.getWritableDatabase();
                                db.execSQL("DROP TABLE IF EXISTS '_" + mlist.get(getAdapterPosition()).getSub_code() + "'");
                                db.close();
                                mlist.remove(mlist.get(getAdapterPosition()));

                                showDailyTimeTable showDailyTimeTable = new showDailyTimeTable();
                                MainActivity.isMainActivityShown=false;
                                FragmentManager fragmentManager =((FragmentActivity) mcontext).getSupportFragmentManager();
                                fragmentManager.popBackStackImmediate();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.content_main,showDailyTimeTable)
                                        .addToBackStack("daily_timetable")
                                        .commit();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            return true;
        }
    }



}
