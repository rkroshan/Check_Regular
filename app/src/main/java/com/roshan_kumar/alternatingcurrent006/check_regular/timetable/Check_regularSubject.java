package com.roshan_kumar.alternatingcurrent006.check_regular.timetable;


import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.roshan_kumar.alternatingcurrent006.check_regular.activities.MainActivity;
import com.roshan_kumar.alternatingcurrent006.check_regular.R;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.Check_regularHelper;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubContract;
/*import com.creator.innov.check_regular.notification.alarmservice;*/

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class Check_regularSubject extends Fragment implements View.OnClickListener {

    //declaring variables
    Button present, absent, submit;
    int i = 0;
    Check_regularHelper checkRegularHelper;
    String subject_code;
    Date dt = new Date();
    String[] s;
    TextView day, date1, heading, present_status, absent_status, total_status;

    public Check_regularSubject() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_check_regular_subject, container, false);

           /* //services starting
            getActivity().startService(new Intent(getActivity(), alarmservice.class));*/

            //declaring var
            dt = new Date();
            s = dt.toString().split(" ");
            String date = s[2] + " " + s[1] + " " + s[5];

            subject_code = getArguments().getString("subjectCode");

            //dec database
            checkRegularHelper = new Check_regularHelper(getActivity(), subject_code);


            present = (Button) view.findViewById(R.id.present);
            absent = (Button) view.findViewById(R.id.absent);
            submit = (Button) view.findViewById(R.id.submit);
            day = (TextView) view.findViewById(R.id.day);
            date1 = (TextView) view.findViewById(R.id.date);
            heading = (TextView) view.findViewById(R.id.heading);
            present_status = (TextView) view.findViewById(R.id.present_status);
            absent_status = (TextView) view.findViewById(R.id.absent_status);
            total_status = (TextView) view.findViewById(R.id.total_status);

            //setting day and date to show
            day.setText(s[0]);
            date1.setText(date);
            heading.setText(subject_code.split("_")[1]);

            //declaring listeners
            present.setOnClickListener(this);
            absent.setOnClickListener(this);
            submit.setOnClickListener(this);

            loaddata();
        }
        //return view
        return view;
    }

    private void loaddata() {
        SQLiteDatabase db = checkRegularHelper.getReadableDatabase();
        String date = s[2] + " " + s[1] + " " + s[5] + " ( " + s[0] + " ) ";
        Cursor c = null;
        try {
            c = db.query(subject_code, new String[]{SubContract.Check_Regular.PRESENT, SubContract.Check_Regular.ABSENT}, SubContract.Check_Regular.DATE + "=?", new String[]{date}, null, null, null);
            if (c != null && c.getCount() != 0) {
                c.moveToFirst();
                int pr = c.getInt(c.getColumnIndexOrThrow(SubContract.Check_Regular.PRESENT));
                int ab = c.getInt(c.getColumnIndexOrThrow(SubContract.Check_Regular.ABSENT));
                present_status.setText("Present : " + pr);
                absent_status.setText("Absent : " + ab);
                total_status.setText("Total : " + (pr + ab));
            }
        } catch (SQLException e) {
        }
    }


    public void present() {
        i = 1;
        present.setBackgroundColor(Color.GREEN);
        absent.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.absent));
        submit.setEnabled(true);
    }

    public void absent() {
        i = -1;
        absent.setBackgroundColor(Color.GREEN);
        present.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.present));
        submit.setEnabled(true);
    }


    public void submit() {

        SQLiteDatabase db = checkRegularHelper.getWritableDatabase();

        String date = s[2] + " " + s[1] + " " + s[5] + " ( " + s[0] + " ) ";
        Log.e("DATE------------------", date);
        ContentValues contentValues = new ContentValues();
        Cursor c = null;
        try {
            c = db.query(subject_code, null, SubContract.Check_Regular.DATE + "=?", new String[]{date}, null, null, null);
        } catch (SQLException e) {
            createUserTable(db);
            c = db.query(subject_code, null, SubContract.Check_Regular.DATE + "=?", new String[]{date}, null, null, null);
        }
        if (!c.moveToFirst()) {
            Log.e("Cursor-----------", String.valueOf(c.moveToFirst()));
            contentValues.put(SubContract.Check_Regular.DATE, date);
            if (i < 0) {
                contentValues.put(SubContract.Check_Regular.ABSENT, i * -1);
                contentValues.put(SubContract.Check_Regular.TOTAL, String.valueOf(i * -1) + " -1");
            } else if (i > 0) {
                contentValues.put(SubContract.Check_Regular.PRESENT, i);
                contentValues.put(SubContract.Check_Regular.TOTAL, String.valueOf(i) + " 1");
            }

            db.insert(subject_code, null, contentValues);

            Toast.makeText(getActivity(), "Successful response", Toast.LENGTH_SHORT).show();
            nextFragment();
        } else {
            String d = c.getString(c.getColumnIndexOrThrow(SubContract.Check_Regular.TOTAL));
            int p = c.getInt(c.getColumnIndexOrThrow(SubContract.Check_Regular.PRESENT));
            int a = c.getInt(c.getColumnIndexOrThrow(SubContract.Check_Regular.ABSENT));
            if (!d.equals("0")) {
                alertbox(d, p, a, db, date);
            } else {
                if (i < 0) {
                    contentValues.put(SubContract.Check_Regular.ABSENT, i * -1);
                    contentValues.put(SubContract.Check_Regular.TOTAL, String.valueOf(i * -1) + " -1");
                } else if (i > 0) {
                    contentValues.put(SubContract.Check_Regular.PRESENT, i);
                    contentValues.put(SubContract.Check_Regular.TOTAL, String.valueOf(i) + " 1");
                }
                db.update(subject_code, contentValues, SubContract.Check_Regular.DATE + "=?", new String[]{date});

                Toast.makeText(getActivity(), "Successful response", Toast.LENGTH_SHORT).show();
                nextFragment();
            }
        }

    }

    private void createUserTable(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + this.subject_code
                + " (" + SubContract.Check_Regular.DATE + " TEXT PRIMARY KEY, " + SubContract.Check_Regular.PRESENT + " INTEGER DEFAULT 0, " +
                SubContract.Check_Regular.ABSENT + " INTEGER DEFAULT 0, " + SubContract.Check_Regular.TOTAL + " TEXT DEFAULT '0')";
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    private void nextFragment() {
        showDailyTimeTable showDailyTimeTable = new showDailyTimeTable();
        MainActivity.isMainActivityShown = false;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, showDailyTimeTable)
                .addToBackStack("daily_timetable")
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.present:
                present();
                break;
            case R.id.absent:
                absent();
                break;
            case R.id.submit:
                submit();
                break;
        }

    }

    private void alertbox(final String d, final int p, final int a, final SQLiteDatabase db, final String date) {
        final String[] t = d.split(" ");
        String message = null;
        String notOneClass = "You already had " + String.valueOf(t[0]) + " classes of " + subject_code.split("_")[1] + "\n" + "Do you want changes in Last Attendance??";
        String oneClass = "You already had " + "1 class of " + subject_code.split("_")[1] + "\n" + "Do you want changes in last Attendance??";
        if (t[0].equals("1")) {
            message = oneClass;
        } else {
            message = notOneClass;
        }

        SharedPreferences preferences = getActivity().getSharedPreferences("student_profile", Context.MODE_PRIVATE);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage(message)
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues contentValues = new ContentValues();
                        if (i < 0 && t[1].equals("1")) {
                            contentValues.put(SubContract.Check_Regular.PRESENT, p - i * -1);
                            contentValues.put(SubContract.Check_Regular.ABSENT, a + i * -1);
                            contentValues.put(SubContract.Check_Regular.TOTAL, String.valueOf(Integer.parseInt(t[0])) + " -1");
                            db.update(subject_code, contentValues, SubContract.Check_Regular.DATE + "=?", new String[]{date});
                        } else if (i > 0 && t[1].equals("-1")) {
                            contentValues.put(SubContract.Check_Regular.ABSENT, a - i);
                            contentValues.put(SubContract.Check_Regular.PRESENT, p + i);
                            contentValues.put(SubContract.Check_Regular.TOTAL, String.valueOf(Integer.parseInt(t[0])) + " 1");
                            db.update(subject_code, contentValues, SubContract.Check_Regular.DATE + "=?", new String[]{date});
                        }
                        Toast.makeText(getActivity(), "Successful response", Toast.LENGTH_SHORT).show();
                        nextFragment();
                    }
                })
                .setNeutralButton("Another Class", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues contentValues = new ContentValues();
                        if (i < 0) {
                            contentValues.put(SubContract.Check_Regular.ABSENT, a + i * -1);
                            contentValues.put(SubContract.Check_Regular.TOTAL, String.valueOf(Integer.parseInt(t[0]) + i * -1) + " -1");
                            db.update(subject_code, contentValues, SubContract.Check_Regular.DATE + "=?", new String[]{date});
                        } else if (i > 0) {
                            contentValues.put(SubContract.Check_Regular.TOTAL, String.valueOf(Integer.parseInt(t[0]) + i) + " 1");
                            contentValues.put(SubContract.Check_Regular.PRESENT, p + i);
                            db.update(subject_code, contentValues, SubContract.Check_Regular.DATE + "=?", new String[]{date});
                        }
                        Toast.makeText(getActivity(), "Successful response", Toast.LENGTH_SHORT).show();
                        nextFragment();
                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.setTitle("Hey, " + preferences.getString("student_name", ""));
        alertDialog.show();
    }
}
