package com.roshan_kumar.alternatingcurrent006.check_regular.timetable;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roshan_kumar.alternatingcurrent006.check_regular.adapter.Myadapter;
import com.roshan_kumar.alternatingcurrent006.check_regular.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Timetable extends Fragment {


    public Timetable() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_timetable, container, false);

            //view pager
            ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            viewPager.setAdapter(new Myadapter(fragmentManager));
        }
        return view;
    }



}