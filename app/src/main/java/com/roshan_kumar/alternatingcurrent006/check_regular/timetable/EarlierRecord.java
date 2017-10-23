package com.roshan_kumar.alternatingcurrent006.check_regular.timetable;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roshan_kumar.alternatingcurrent006.check_regular.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarlierRecord extends Fragment implements View.OnClickListener {

    public static final String earlier = "Earlier_record";

    TextView scode;
    EditText attend, total;
    Button submit;
    String s;


    public EarlierRecord() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_earlier_record, container, false);

            //get arguments
            s = getArguments().getString("subjectCode", "");

            //dec var
            scode = (TextView) view.findViewById(R.id.code);
            attend = (EditText) view.findViewById(R.id.attend);
            total = (EditText) view.findViewById(R.id.total);
            submit = (Button) view.findViewById(R.id.submitthis);

            //setting scode
            scode.setText(s);

            //set listener
            submit.setOnClickListener(this);


        }
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitthis) {
            String attendd = attend.getText().toString();
            String totall=total.getText().toString();
            if(!totall.isEmpty()) {
                int tt = Integer.parseInt(totall);
                int pp = Integer.parseInt(attendd);
                if(tt!=0&&tt>pp) {
                    SharedPreferences preferences = getActivity().getSharedPreferences(earlier, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(s, attendd + " " + totall);
                    editor.apply();

                    Toast.makeText(getActivity(), "Record Submitted", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(),"Wrong Data",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
