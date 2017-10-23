package com.roshan_kumar.alternatingcurrent006.check_regular.notification;


import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.roshan_kumar.alternatingcurrent006.check_regular.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reminder extends Fragment implements View.OnClickListener {

    EditText rem1,rem2,rem3;
    Button set;

    public Reminder() {
        // Required empty public constructor
    }

View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_reminder, container, false);

            //dec var
            rem1=(EditText) view.findViewById(R.id.reminder1);
            rem2 = (EditText) view.findViewById(R.id.reminder2);
            rem3 = (EditText) view.findViewById(R.id.reminder3);
            set = (Button) view.findViewById(R.id.set);

            //listener
            rem1.setOnClickListener(this);
            rem2.setOnClickListener(this);
            rem3.setOnClickListener(this);
            set.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(final View v) {
        if(v.getId()==R.id.set){
            SharedPreferences preferences = getActivity().getSharedPreferences("Reminder", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("setReminder1",rem1.getText().toString());
            editor.putString("setReminder2",rem2.getText().toString());
            editor.putString("setReminder3",rem3.getText().toString());
            editor.apply();

            getActivity().startService(new Intent(getActivity(),alarmservice.class));

            Toast.makeText(getActivity(),"Reminder Set",Toast.LENGTH_SHORT).show();
        }else {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(android.widget.TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String hr,min;
                    if(selectedHour<10) {hr = ("0"+String.valueOf(selectedHour));}
                    else hr=String.valueOf(selectedHour);
                    if(selectedMinute<10) {min = ("0"+String.valueOf(selectedMinute));}
                    else min = String.valueOf(selectedMinute);
                    ((EditText) v.findViewById(v.getId())).setText(hr + " " + min);
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
    }
}
