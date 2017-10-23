package com.roshan_kumar.alternatingcurrent006.check_regular.about_us;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roshan_kumar.alternatingcurrent006.check_regular.R;

/**
 * Created by CREATOR on 10/1/2017.
 */

public class AboutMe extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.about_us, container, false);
        }
        return view;
    }
}
