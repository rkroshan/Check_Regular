package com.roshan_kumar.alternatingcurrent006.check_regular.activities.filling_data;


import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.roshan_kumar.alternatingcurrent006.check_regular.R;
import com.roshan_kumar.alternatingcurrent006.check_regular.activities.MainActivity;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubContract;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubContract.SubEntry;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubDbHelper;
import com.roshan_kumar.alternatingcurrent006.check_regular.timetable.showDailyTimeTable;
import com.roshan_kumar.alternatingcurrent006.check_regular.timetable.subject_show;

import java.util.Calendar;

import static com.roshan_kumar.alternatingcurrent006.check_regular.R.id;
import static com.roshan_kumar.alternatingcurrent006.check_regular.R.layout;

/*import com.creator.innov.check_regular.notification.alarmservice;*/


/**
 * A simple {@link Fragment} subclass.
 */
public class subject_edit extends Fragment implements View.OnClickListener {

    //declaring variables
   /* TextView subject_no;*/
    EditText subject_name, sub_code;
    CheckBox check_mon, check_tue, check_wed, check_thur, check_fri, check_sat, check_sun;
    SubDbHelper subDbHelper;

    //timetext
    EditText mfrom, tufrom, wfrom, thfrom, ffrom, stfrom, sufrom;

    Button done;

    //strings
    String mon,tue,wed,thu,fri,sat,sun,subcode;


    public subject_edit() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null) {
            view = inflater.inflate(layout.activity_subject_edit,container,false);

            //getting subcode
            subcode=getArguments().getString("subjectCode");

            //database decalaration
            subDbHelper = new SubDbHelper(getActivity());

            //decalaring buttons
            done = (Button) view.findViewById(id.done_button);

            //adding listeners
            done.setOnClickListener(this);


            declaringViews();

        }
        return view;
    }

    private void declaringViews() {
        subject_name = (EditText) view.findViewById(id.subject_name);
        sub_code = (EditText) view.findViewById(id.sub_code);
        check_mon = (CheckBox) view.findViewById(id.check_mon);
        check_tue = (CheckBox) view.findViewById(id.check_tue);
        check_wed = (CheckBox) view.findViewById(id.check_wed);
        check_thur = (CheckBox) view.findViewById(id.check_thurs);
        check_fri = (CheckBox) view.findViewById(id.check_fri);
        check_sat = (CheckBox) view.findViewById(id.check_sat);
        check_sun = (CheckBox) view.findViewById(id.check_sun);

        mfrom = (EditText) view.findViewById(id.mFrom);
        mfrom.setOnClickListener(this);
        tufrom = (EditText) view.findViewById(id.tuFrom);
        tufrom.setOnClickListener(this);
        wfrom = (EditText) view.findViewById(id.wFrom);
        wfrom.setOnClickListener(this);
        thfrom = (EditText) view.findViewById(id.thFrom);
        thfrom.setOnClickListener(this);
        ffrom = (EditText) view.findViewById(id.fFrom);
        ffrom.setOnClickListener(this);
        stfrom = (EditText) view.findViewById(id.stFrom);
        stfrom.setOnClickListener(this);
        sufrom = (EditText) view.findViewById(id.suFrom);
        sufrom.setOnClickListener(this);

        existingData();
    }

    private void existingData(){
        SQLiteDatabase db = subDbHelper.getReadableDatabase();
        Cursor c = db.query(SubEntry.TABLE_NAME,null,SubEntry.SUB_CODE+"=?",new String[]{subcode},null,null,null);

        c.moveToFirst();
        do{
            subject_name.setText(c.getString(c.getColumnIndexOrThrow(SubEntry.SUB_NAME)));
            sub_code.setText(subcode);
            sub_code.setEnabled(false);

            mon = c.getString(c.getColumnIndexOrThrow(SubEntry.DAY_MON));
            if(!mon.equals("a")){
                check_mon.setChecked(true);
                if(!mon.equals("1"))
                    mfrom.setText(mon);
            }
            tue = c.getString(c.getColumnIndexOrThrow(SubEntry.DAY_TUE));
            if(!tue.equals("a")){
                check_tue.setChecked(true);
                if(!tue.equals("1"))
                    tufrom.setText(mon);
            }
            wed = c.getString(c.getColumnIndexOrThrow(SubEntry.DAY_WED));
            if(!wed.equals("a")){
                check_wed.setChecked(true);
                if(!wed.equals("1"))
                    wfrom.setText(mon);
            }
            thu = c.getString(c.getColumnIndexOrThrow(SubEntry.DAY_THU));
            if(!thu.equals("a")){
                check_thur.setChecked(true);
                if(!thu.equals("1"))
                    thfrom.setText(mon);
            }
            fri = c.getString(c.getColumnIndexOrThrow(SubEntry.DAY_FRI));
            if(!fri.equals("a")){
                check_fri.setChecked(true);
                if(!fri.equals("1"))
                    ffrom.setText(mon);
            }
            sat = c.getString(c.getColumnIndexOrThrow(SubEntry.DAY_SAT));
            if(!sat.equals("a")){
                check_sat.setChecked(true);
                if(!sat.equals("1"))
                    stfrom.setText(mon);
            }
            sun = c.getString(c.getColumnIndexOrThrow(SubEntry.DAY_SUN));
            if(!sun.equals("a")){
                check_sun.setChecked(true);
                if(!sun.equals("1"))
                    sufrom.setText(mon);
            }
        }while (c.moveToNext());

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {case id.done_button:
                if (sub_code.getText().toString().equals(null) || sub_code.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please insert Subject Code", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    int i = dataSubmit();
                    if (i != -1) {
                        changeactivity1();
                    }
                }
                break;

            default:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hr, min;
                        if (selectedHour < 10) {
                            hr = ("0" + String.valueOf(selectedHour));
                        } else hr = String.valueOf(selectedHour);
                        if (selectedMinute < 10) {
                            min = ("0" + String.valueOf(selectedMinute));
                        } else min = String.valueOf(selectedMinute);
                        ((EditText) v.findViewById(v.getId())).setText(hr + ":" + min);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

        }

    }

   /* private void switchFragment(){
        showDailyTimeTable showDailyTimeTable = new showDailyTimeTable();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main,showDailyTimeTable)
                .addToBackStack("daily_timetable")
                .commit();
    }*/

    private void changeactivity1() {
        subject_show subjectShow = new subject_show("edit_subject");
        MainActivity.isMainActivityShown = false;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, subjectShow)
                .addToBackStack("all_subjects")
                .commit();
    }

    private int dataSubmit() {
        String sub = sub_code.getText().toString();
        String name = subject_name.getText().toString().toUpperCase();
        SQLiteDatabase db = subDbHelper.getWritableDatabase();
        sub = sub.toUpperCase();

        String s;

        ContentValues contentValues = new ContentValues();
        contentValues.put(SubEntry.SUB_NAME, name);

        if (check_mon.isChecked()) {
            s = mfrom.getText().toString();
            if (!s.isEmpty()) {contentValues.put(SubEntry.DAY_MON, s);}
        }

        if (check_tue.isChecked()) {
            s = tufrom.getText().toString();
            if (!s.isEmpty()) {contentValues.put(SubEntry.DAY_TUE, s); }
        }


        if (check_wed.isChecked()) {
            s = wfrom.getText().toString();
            if (!s.isEmpty()) {contentValues.put(SubEntry.DAY_WED, s);}
        }

        if (check_thur.isChecked()) {
            s = thfrom.getText().toString();
            if (!s.isEmpty()) {contentValues.put(SubEntry.DAY_THU, s);}
        }

        if (check_fri.isChecked()) {
            s = ffrom.getText().toString();
            if (!s.isEmpty()) {contentValues.put(SubEntry.DAY_FRI, s);}
        }

        if (check_sat.isChecked()) {
            s = stfrom.getText().toString();
            if (!s.isEmpty()) {contentValues.put(SubEntry.DAY_SAT, s);}
        }

        if (check_sun.isChecked()) {
            s = sufrom.getText().toString();
            if (!s.isEmpty()) {contentValues.put(SubEntry.DAY_SUN, s);}
        }

        long response = db.update(SubEntry.TABLE_NAME,contentValues, SubEntry.SUB_CODE+"=?",new String[]{sub});
        if (response !=1) {
            Toast.makeText(getActivity(), "Subject code " + sub + " already exist", Toast.LENGTH_LONG).show();
            return -1;
        } else
            Toast.makeText(getActivity(), name + " ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();

        return 1;
    }

}
