package com.roshan_kumar.alternatingcurrent006.check_regular.timetable;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
public class Predictor extends Fragment implements View.OnClickListener {

    EditText attend1, total1;
    Button predict;
    TextView percent, predict_result, future_predict;
    SharedPreferences preferences;


    public Predictor() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_predictor, container, false);

            //initialize the var
            attend1 = (EditText) view.findViewById(R.id.attend1);
            total1 = (EditText) view.findViewById(R.id.total1);
            predict = (Button) view.findViewById(R.id.predict);
            percent = (TextView) view.findViewById(R.id.percent);
            predict_result = (TextView) view.findViewById(R.id.predict_result);
            future_predict = (TextView) view.findViewById(R.id.future_predict);

            //set listener
            predict.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.predict) {
            int present, total;
            float required;
            present = Integer.parseInt(attend1.getText().toString());
            total = Integer.parseInt(total1.getText().toString());
            preferences = getActivity().getSharedPreferences("student_profile", Context.MODE_PRIVATE);
            required = preferences.getFloat("required_percent", 0);

            if (present < 0 || total < 0) {
                Toast.makeText(getActivity(), "Don't put a negative number :(", Toast.LENGTH_LONG).show();
            } else if (total == 0) {
                Toast.makeText(getActivity(), "Sorry, Total classes can't be 0", Toast.LENGTH_SHORT).show();
            } else if (total < present) {
                Toast.makeText(getActivity(), "Sorry, Total classes can't be less than Attended classes", Toast.LENGTH_LONG).show();
            } else {
                float ans = Float.valueOf(present) * 100 / total;
                percent.setText(String.format("%.02f", ans) + "%");

                if (ans == required) {
                    predict_result.setTextColor(ContextCompat.getColor(getActivity(), R.color.green));
                    predict_result.setText("Enjoy :)");
                    future_predict.setText("You are Up To Date....");
                } else if (ans > required) {
                    predict_result.setTextColor(ContextCompat.getColor(getActivity(), R.color.green));
                    predict_result.setText("Enjoy :)");
                    future_predict.setText("You Seems to be " + "\nA Punctual Student....");
                } else {
                    int result = (int) Math.ceil(Float.valueOf(present * 100 - required * total) / (required - 100));
                    if (result == 1) {
                        predict_result.setTextColor(ContextCompat.getColor(getActivity(), R.color.nocolor));
                        predict_result.setText("Cool :)");
                        future_predict.setText("You have to attend \n" + String.valueOf(result) + "\nUpcoming Class ...");

                    } else {
                        predict_result.setTextColor(ContextCompat.getColor(getActivity(), R.color.absent));
                        predict_result.setText("BE REGULAR :(");
                        future_predict.setText("You have to attend \n" + String.valueOf(result) + "\nUpcoming Classes Regulary...");
                    }
                }
            }
        }
    }
}
