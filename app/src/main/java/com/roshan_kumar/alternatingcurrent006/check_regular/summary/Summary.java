package com.roshan_kumar.alternatingcurrent006.check_regular.summary;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.roshan_kumar.alternatingcurrent006.check_regular.activities.MainActivity;
import com.roshan_kumar.alternatingcurrent006.check_regular.R;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.Check_regularHelper;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubContract;
import com.roshan_kumar.alternatingcurrent006.check_regular.timetable.EarlierRecord;

/**
 * A simple {@link Fragment} subclass.
 */
public class Summary extends Fragment implements View.OnClickListener {
    TextView sname,scode,status,percentage,greet;
    Button details;
    String Sname,Scode,Scodepref;
    Check_regularHelper checkRegularHelper;
    int present,total;
    float prec;
    SharedPreferences preferences;

    public Summary() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getActivity().getActionBar().setTitle("Summary");
        if(view==null) {
            view = inflater.inflate(R.layout.summary, container, false);

            //getting arguments
            Sname = getArguments().getString("subjectName");
            Scode = getArguments().getString("subjectCode");
            Scodepref = Scode.split("_")[1];

            //pref
            SharedPreferences preferences = this.getActivity().getSharedPreferences("database_version", 0);


            //dec database
            checkRegularHelper = new Check_regularHelper(getActivity(),Scode);

            declaraingVar(view);
            makingSummary();
        }
        return view;
    }

    private void declaraingVar(View view) {
        sname = (TextView) view.findViewById(R.id.sname);
        scode = (TextView) view.findViewById(R.id.scode);
        status=(TextView) view.findViewById(R.id.attendance);
        percentage=(TextView) view.findViewById(R.id.percentage);
        greet = (TextView) view.findViewById(R.id.greet);

        details = (Button) view.findViewById(R.id.details);
        details.setOnClickListener(this);
    }

    private void makingSummary() {
        sname.setText(Sname.toUpperCase());
        scode.setText(Scodepref.toUpperCase());
        loadData();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.details){
            Subject_summary subjectSummary = new Subject_summary();
            MainActivity.isMainActivityShown=false;
            Bundle args=new Bundle();
            args.putString("Scode",Scode);
            args.putString("Sname",Sname);
            subjectSummary.setArguments(args);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main,subjectSummary)
                    .addToBackStack("Subject_summary")
                    .commit();
        }
    }


    private void loadData() {
        Cursor cursor = query();

            if (cursor != null && cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int i = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(SubContract.Check_Regular.PRESENT)));
                    String j = (cursor.getString(cursor.getColumnIndexOrThrow(SubContract.Check_Regular.TOTAL)));
                    present += i;
                    if (j.equals("0")) total += 0;
                    else {
                        String[] t = j.split(" ");
                        total += Integer.parseInt(t[0]);
                    }
                } while (cursor.moveToNext());

                try {
                    //calling preferences
                    preferences = getActivity().getSharedPreferences(EarlierRecord.earlier, Context.MODE_PRIVATE);
                    String s[] = preferences.getString(Scodepref, "").split(" ");
                    present += Integer.parseInt(s[0]);
                    total += Integer.parseInt(s[1]);
                } catch (Exception e) {

                }
                settext(total,present);
            }
        else {
                try {
                    //calling preferences
                    preferences = getActivity().getSharedPreferences(EarlierRecord.earlier, Context.MODE_PRIVATE);
                    String s[] = preferences.getString(Scodepref, "").split(" ");
                    present = Integer.parseInt(s[0]);
                    total = Integer.parseInt(s[1]);
                } catch (Exception e) {

                }
                settext(total, present);
            }

    }

    private void settext(int total1,int present1){
            //status update
            status.setText(String.valueOf(present1) + "/" + String.valueOf(total1));
            //percentage update

                prec = (Float.valueOf(present1) / total1) * 100;
            if(total1!=0) {
                percentage.setText(String.format("%.02f",prec) + "%");
            }else{
                percentage.setText("0");
            }
            //greet update
            preferences = getActivity().getSharedPreferences("student_profile", Context.MODE_PRIVATE);
            if (prec >= preferences.getFloat("required_percent",0)) {
                greet.setText("Congrats :)");
            }else if(total1==0){
                greet.setTextColor(ContextCompat.getColor(getContext(), R.color.nocolor));
                greet.setText("Enjoy! No Class");
            }
            else {
                greet.setTextColor(ContextCompat.getColor(getContext(), R.color.OMG));
                greet.setText("OMG !");
            }
        }


    private Cursor query() {
        SQLiteDatabase db = checkRegularHelper.getReadableDatabase();
        try {
            Cursor c = db.query(Scode, new String[]{SubContract.Check_Regular.PRESENT,SubContract.Check_Regular.TOTAL}, null, null, null, null, null);
            return c;
        }catch (SQLException e){
            details.setVisibility(View.GONE);

            try {
                //calling preferences
                preferences = getActivity().getSharedPreferences(EarlierRecord.earlier, Context.MODE_PRIVATE);
                String s[] = preferences.getString(Scodepref, "").split(" ");
                present = Integer.parseInt(s[0]);
                total = Integer.parseInt(s[1]);
            } catch (Exception s) {

            }
            settext(total,present);
        }
        return null;
    }
}
