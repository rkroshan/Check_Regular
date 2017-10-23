package com.roshan_kumar.alternatingcurrent006.check_regular.timetable;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roshan_kumar.alternatingcurrent006.check_regular.activities.MainActivity;
import com.roshan_kumar.alternatingcurrent006.check_regular.R;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.Check_regularHelper;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubDbHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClearRecords extends Fragment {


    public ClearRecords() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_clear_records, container, false);
            alertdialogbox();
        }
        return view;
    }

    private void alertdialogbox() {
        SharedPreferences preferences = getActivity().getSharedPreferences("student_profile", Context.MODE_PRIVATE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you wanna delete all you saved data " + "\n" + "includes\n\n" + "1. All your TimeTable\n\n" + "2. All your Attendence   ??")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().deleteDatabase(SubDbHelper.DATABASE_NAME);
                        getActivity().deleteDatabase(Check_regularHelper.DATABASE_NAME);
                        getActivity().getSharedPreferences(EarlierRecord.earlier, Context.MODE_PRIVATE).edit().clear().apply();
                        getActivity().finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        switchfragment();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Hey, " + preferences.getString("student_name", ""));
        alertDialog.show();
    }

    private void switchfragment() {
        subject_show subjectShow = new subject_show("all_subject");
        MainActivity.isMainActivityShown = false;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, subjectShow)
                .addToBackStack("all_subjects")
                .commit();
    }
}
