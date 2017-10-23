package com.roshan_kumar.alternatingcurrent006.check_regular.activities.filling_data;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.roshan_kumar.alternatingcurrent006.check_regular.R;
import com.roshan_kumar.alternatingcurrent006.check_regular.activities.MainActivity;
import com.roshan_kumar.alternatingcurrent006.check_regular.notification.alarmservice;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentProfile extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences preferences;
    EditText sname, sid, branch, college, required_percent;
    Button submit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_profile);

        //var dec
        sname = (EditText) findViewById(R.id.student_name);
        sid = (EditText) findViewById(R.id.student_id);
        branch = (EditText) findViewById(R.id.student_branch);
        college = (EditText) findViewById(R.id.student_college);
        required_percent = (EditText) findViewById(R.id.required_percent);
        submit = (Button) findViewById(R.id.submitprofile);

        //set listener
        submit.setOnClickListener(this);


        try {
            preferences = getSharedPreferences("student_profile", Context.MODE_PRIVATE);
            sname.setText(preferences.getString("student_name", ""));
            sid.setText(preferences.getString("student_id", ""));
            branch.setText(preferences.getString("student_branch", ""));
            college.setText(preferences.getString("student_college", ""));
            required_percent.setText(String.valueOf(preferences.getFloat("required_percent", 0)));
        } catch (Exception e) {
        }


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitprofile) {
            preferences = getSharedPreferences("student_profile", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            String s = sname.getText().toString();
            if (!(s.equals(null) || s.equals(""))) {
                editor.putString("student_name", s);
                editor.putString("student_id", sid.getText().toString());
                editor.putString("student_branch", branch.getText().toString());
                editor.putString("student_college", college.getText().toString());
                String p = required_percent.getText().toString();
                if (!(p.equals("") || p.equals(null))) {
                    float percent = Float.valueOf(p);
                    if (percent > 0 && percent <= 100) {
                        editor.putFloat("required_percent", percent);
                        editor.apply();

                        Toast.makeText(this, "Thanks " + sname.getText().toString(), Toast.LENGTH_SHORT).show();
                        changeactivity();
                    } else {
                        Toast.makeText(this, "Please insert appropriate Attendance%", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please insert appropriate Attendance%", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please Insert Your Name", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void changeactivity() {
        SharedPreferences preferences = getSharedPreferences("comingfromStudentProfile", MODE_PRIVATE);
        if (!preferences.getBoolean("comingfromStudentProfile", false)) {
            startService(new Intent(this,alarmservice.class));
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("comingfromStudentProfile", true);
        editor.apply();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
