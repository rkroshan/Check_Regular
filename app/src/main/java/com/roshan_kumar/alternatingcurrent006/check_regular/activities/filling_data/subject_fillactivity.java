package com.roshan_kumar.alternatingcurrent006.check_regular.activities.filling_data;


import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.roshan_kumar.alternatingcurrent006.check_regular.activities.MainActivity;
import com.roshan_kumar.alternatingcurrent006.check_regular.R;
/*import com.creator.innov.check_regular.notification.alarmservice;*/
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubContract.SubEntry;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubDbHelper;

import java.util.Calendar;

import static com.roshan_kumar.alternatingcurrent006.check_regular.R.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class subject_fillactivity extends AppCompatActivity implements View.OnClickListener {

    //declaring variables
   /* TextView subject_no;*/
    EditText subject_name, sub_code;
    CheckBox check_mon, check_tue, check_wed, check_thur, check_fri, check_sat, check_sun;
    SubDbHelper subDbHelper;

    Button add_subject, done;


    public subject_fillactivity() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_subject_fill);

        //database decalaration
        subDbHelper = new SubDbHelper(this);

        //decalaring buttons
        add_subject = (Button) findViewById(id.add_subject_Button);
        done = (Button) findViewById(id.done_button);

        //adding listeners
        add_subject.setOnClickListener(this);
        done.setOnClickListener(this);


        declaringViews();

    }

    private void declaringViews() {
        /*subject_no = (TextView) view.findViewById(R.id.subject_no);*/
        subject_name = (EditText) findViewById(R.id.subject_name);
        sub_code = (EditText) findViewById(R.id.sub_code);
        check_mon = (CheckBox) findViewById(R.id.check_mon);
        check_tue = (CheckBox) findViewById(R.id.check_tue);
        check_wed = (CheckBox) findViewById(R.id.check_wed);
        check_thur = (CheckBox) findViewById(R.id.check_thurs);
        check_fri = (CheckBox) findViewById(R.id.check_fri);
        check_sat = (CheckBox) findViewById(R.id.check_sat);
        check_sun = (CheckBox) findViewById(R.id.check_sun);


    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case id.add_subject_Button:
                if (sub_code.getText().toString().equals(null) || sub_code.getText().toString().equals("")) {
                    Toast.makeText(this, "PLease insert Appropriate Data", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    int i = dataSubmit();
                    if (i != -1) {
                        clearALL();
                    }
                }
                break;

            case id.done_button:
                if (sub_code.getText().toString().equals(null) || sub_code.getText().toString().equals("")) {
                    Toast.makeText(this, "PLease insert Subject Code", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    int i = dataSubmit();
                    if (i != -1) {
                        changeactivity1();
                    }
                }
                break;

        }

    }

    private void changeactivity1() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private int dataSubmit() {
        String sub = sub_code.getText().toString();
        String name = subject_name.getText().toString().toUpperCase();
        SQLiteDatabase db = subDbHelper.getWritableDatabase();
        sub = sub.toUpperCase();



        ContentValues contentValues = new ContentValues();
        contentValues.put(SubEntry.SUB_NAME, name);
        contentValues.put(SubEntry.SUB_CODE, sub);

        if (check_mon.isChecked()) {contentValues.put(SubEntry.DAY_MON, "1");}


        if (check_tue.isChecked()) {contentValues.put(SubEntry.DAY_TUE, "1");}


        if (check_wed.isChecked()) {contentValues.put(SubEntry.DAY_WED, "1");}

        if (check_thur.isChecked()) {contentValues.put(SubEntry.DAY_THU, "1");}


        if (check_fri.isChecked()) {contentValues.put(SubEntry.DAY_FRI, "1");}

        if (check_sat.isChecked()) {contentValues.put(SubEntry.DAY_SAT, "1");}

        if (check_sun.isChecked()) {contentValues.put(SubEntry.DAY_SUN, "1");}

        long response = db.insert(SubEntry.TABLE_NAME, null, contentValues);
        if (response == -1) {
            Toast.makeText(this, "Subject code " + sub + " already exist", Toast.LENGTH_LONG).show();
            return -1;
        } else
            Toast.makeText(this, name + " ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        return 1;
    }

    private void clearALL() {
       /* int no = Integer.valueOf(subject_no.getText().toString())+1;
        subject_no.setText(Integer.toString(no));*/
        subject_name.setText(null);
        sub_code.setText(null);
        check_mon.setChecked(false);
        check_tue.setChecked(false);
        check_wed.setChecked(false);
        check_thur.setChecked(false);
        check_fri.setChecked(false);
        check_sat.setChecked(false);
        check_sun.setChecked(false);
    }

}
