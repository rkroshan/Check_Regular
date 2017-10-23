package com.roshan_kumar.alternatingcurrent006.check_regular.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.roshan_kumar.alternatingcurrent006.check_regular.R;
import com.roshan_kumar.alternatingcurrent006.check_regular.TimetableSharing.ShareTimeTable;
import com.roshan_kumar.alternatingcurrent006.check_regular.about_us.AboutMe;
import com.roshan_kumar.alternatingcurrent006.check_regular.activities.filling_data.StudentProfile;
import com.roshan_kumar.alternatingcurrent006.check_regular.activities.filling_data.subject_fillactivity;
/*import com.creator.innov.check_regular.notification.alarmservice;*/
import com.roshan_kumar.alternatingcurrent006.check_regular.notification.Reminder;
import com.roshan_kumar.alternatingcurrent006.check_regular.timetable.ClearRecords;
import com.roshan_kumar.alternatingcurrent006.check_regular.timetable.Predictor;
import com.roshan_kumar.alternatingcurrent006.check_regular.timetable.Timetable;
import com.roshan_kumar.alternatingcurrent006.check_regular.timetable.showDailyTimeTable;
import com.roshan_kumar.alternatingcurrent006.check_regular.timetable.subject_show;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener/*,TimePickerFragment.PickTime*/ {

    private AdView adView;


    public static Boolean isMainActivityShown = false;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //student profile  to show
        preferences = getSharedPreferences("comingfromStudentProfile", MODE_PRIVATE);
        if (!preferences.getBoolean("comingfromStudentProfile", false)) {
            startActivity(new Intent(this, StudentProfile.class));
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        try {
            preferences = getSharedPreferences("student_profile", Context.MODE_PRIVATE);
            View header = navigationView.getHeaderView(0);
            TextView name = (TextView) header.findViewById(R.id.head);
            TextView detail = (TextView) header.findViewById(R.id.body1);
            TextView detail1 = (TextView) header.findViewById(R.id.body3);
            name.setText(preferences.getString("student_name", ""));
            detail.setText(preferences.getString("student_id", "") + " , " + preferences.getString("student_branch", ""));
            detail1.setText(preferences.getString("student_college", ""));
        } catch (Exception e) {
        }
        navigationView.setNavigationItemSelectedListener(this);

        MobileAds.initialize(this, String.valueOf(R.string.ADMOB_APP_ID));
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);

        showDailyTimeTable showDailyTimeTable = new showDailyTimeTable();
        isMainActivityShown = false;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, showDailyTimeTable)
                .addToBackStack("daily_timetable")
                .commit();
    }


    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (isMainActivityShown)
            finish();
        else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.guide:
                startActivity(new Intent(MainActivity.this, guide.class));
                return true;
            case R.id.reminder:
                Reminder reminder = new Reminder();
                isMainActivityShown = false;
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, reminder)
                        .addToBackStack("reminder")
                        .commit();
                break;
            case R.id.share_menu_item:
                ShareTimeTable shareTimeTable = new ShareTimeTable();
                isMainActivityShown = false;
                FragmentManager fragmentManager1 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                fragmentTransaction1.replace(R.id.content_main, shareTimeTable)
                        .addToBackStack("share_timeTable")
                        .commit();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sub_fill) {
            startActivity(new Intent(MainActivity.this, subject_fillactivity.class));
        } else if (id == R.id.daily_timetable) {
            showDailyTimeTable showDailyTimeTable = new showDailyTimeTable();
            isMainActivityShown = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, showDailyTimeTable)
                    .addToBackStack("daily_timetable")
                    .commit();
            //getSupportActionBar().setTitle("Daily TimeTable");

        } else if (id == R.id.all_subjects) {
            subject_show subjectShow = new subject_show("all_subject");
            isMainActivityShown = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, subjectShow)
                    .addToBackStack("all_subjects")
                    .commit();
            // getSupportActionBar().setTitle("Subjects");

        } else if (id == R.id.TIMETABLE) {
            Timetable timetable = new Timetable();
            isMainActivityShown = true;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStackImmediate();
            }
            fragmentTransaction.replace(R.id.content_main, timetable)
                    .addToBackStack("tt")
                    .commit();
        } else if (id == R.id.summary) {
            subject_show subjectShow = new subject_show("summary");
            isMainActivityShown = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, subjectShow)
                    .addToBackStack("summary")
                    .commit();
            //getSupportActionBar().setTitle("Summary");
        } else if (id == R.id.earlier_record) {
            subject_show subjectShow = new subject_show("earlier_record");
            isMainActivityShown = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, subjectShow)
                    .addToBackStack("all_subjects")
                    .commit();

        } else if (id == R.id.predict_frag) {
            Predictor predictor = new Predictor();
            isMainActivityShown = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, predictor)
                    .addToBackStack("predictor")
                    .commit();
        } else if (id == R.id.clear_records) {
            ClearRecords clearRecords = new ClearRecords();
            isMainActivityShown = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, clearRecords)
                    .addToBackStack("clearRecords")
                    .commit();
        } else if (id == R.id.student_profile) {
            startActivity(new Intent(MainActivity.this, StudentProfile.class));
        } else if (id == R.id.About_Us) {
            AboutMe aboutMe = new AboutMe();
            isMainActivityShown = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, aboutMe)
                    .addToBackStack("About_us")
                    .commit();
        }else if(id==R.id.edit_sub){
            subject_show subjectShow = new subject_show("edit_subject");
            isMainActivityShown = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, subjectShow)
                    .addToBackStack("all_subjects")
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}


