package com.roshan_kumar.alternatingcurrent006.check_regular.TimetableSharing;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.roshan_kumar.alternatingcurrent006.check_regular.R;
import com.roshan_kumar.alternatingcurrent006.check_regular.activities.MainActivity;
import com.roshan_kumar.alternatingcurrent006.check_regular.activities.filling_data.subject_fillactivity;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.Check_regularHelper;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubContract;
import com.roshan_kumar.alternatingcurrent006.check_regular.data.SubDbHelper;
import com.roshan_kumar.alternatingcurrent006.check_regular.timetable.EarlierRecord;
import com.roshan_kumar.alternatingcurrent006.check_regular.timetable.showDailyTimeTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.R.attr.data;
import static android.app.Activity.RESULT_OK;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareTimeTable extends Fragment implements View.OnClickListener {

    private static final int FILE_PICK = 71;
    private static final int READ_WRITE_STORAGE = 72;
    Button addsubject,importTimetable,share;
    private static final String Filename = "Check_Regular.txt";
    private static final String check = "check_regular";


    public ShareTimeTable() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_share_time_table, container, false);

            addsubject = (Button) view.findViewById(R.id.add_subject);
            importTimetable = (Button) view.findViewById(R.id.import_timetable);
            share = (Button) view.findViewById(R.id.share);

            addsubject.setOnClickListener(this);
            importTimetable.setOnClickListener(this);
            share.setOnClickListener(this);

            checkpermissions();
        }
    return view;
    }

    private void checkpermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_WRITE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(!(requestCode==READ_WRITE_STORAGE&&grantResults[0]==PackageManager.PERMISSION_GRANTED)){
            Toast.makeText(getActivity(),"Permission_denied",Toast.LENGTH_LONG).show();
            switchfragment();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.add_subject:
                addSubject();
                break;
            case R.id.import_timetable:
                import_Timetable();
                break;
            case R.id.share:
                share_timetable();
        }

    }

    private void addSubject() {
        startActivity(new Intent(getActivity(),subject_fillactivity.class));
    }

    private void share_timetable() {
        int i = write();
        if(i==1) {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + check, Filename);
            Intent sharingintent = new Intent(Intent.ACTION_SEND);
            sharingintent.setType("text/*");
            sharingintent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
            startActivity(Intent.createChooser(sharingintent, "share file with"));
        }
    }

    private void import_Timetable() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/*");
        startActivityForResult(intent, FILE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==FILE_PICK && resultCode==RESULT_OK){
            Uri uri = data.getData();
            read_file(uri);
        }
    }

    private void read_file(Uri uri) {
        BufferedReader bufferReader;
        int i = 0;
        try {
            bufferReader = new BufferedReader(new InputStreamReader(getActivity().getContentResolver().openInputStream(uri)));
            String line = "";

            do {
                line += bufferReader.readLine();
            } while (bufferReader.readLine() != null);

             alertdialogbox(line);

        } catch (FileNotFoundException e) {
            Toast.makeText(getContext(), "File Not Found", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void switchfragment() {
        showDailyTimeTable showDailyTimeTable = new showDailyTimeTable();
        MainActivity.isMainActivityShown = false;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, showDailyTimeTable)
                .addToBackStack("daily_timetable")
                .commit();
    }

    private int write() {
        String timetable_data=check;
        SubDbHelper subDbhelper = new SubDbHelper(getActivity());
        SQLiteDatabase db = subDbhelper.getReadableDatabase();
        try {
            Cursor c = db.query(SubContract.SubEntry.TABLE_NAME, null, null, null, null, null, null);

            if(c.getCount()==0){
                Toast.makeText(getContext(),"No TimeTable Exists",Toast.LENGTH_SHORT).show();
                return -1;
            }
            c.moveToFirst();
            do{
                timetable_data+="-"+c.getString(c.getColumnIndexOrThrow(SubContract.SubEntry.SUB_NAME))+";"
                        +c.getString(c.getColumnIndexOrThrow(SubContract.SubEntry.SUB_CODE))+";"
                        +c.getString(c.getColumnIndexOrThrow(SubContract.SubEntry.DAY_MON))+";"
                        +c.getString(c.getColumnIndexOrThrow(SubContract.SubEntry.DAY_TUE))+";"
                        +c.getString(c.getColumnIndexOrThrow(SubContract.SubEntry.DAY_WED))+";"
                        +c.getString(c.getColumnIndexOrThrow(SubContract.SubEntry.DAY_THU))+";"
                        +c.getString(c.getColumnIndexOrThrow(SubContract.SubEntry.DAY_FRI))+";"
                        +c.getString(c.getColumnIndexOrThrow(SubContract.SubEntry.DAY_SAT))+";"
                        +c.getString(c.getColumnIndexOrThrow(SubContract.SubEntry.DAY_SUN));

            }while (c.moveToNext());
            File file = new File(Environment.getExternalStorageDirectory()+"/"+check,Filename);
            if(!file.exists())file.getParentFile().mkdirs();
            file.setWritable(true);
            file.setReadable(true);
            FileOutputStream fileOutputStream;

            try {
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(timetable_data.getBytes());
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (SQLException s){
            Toast.makeText(getContext(),"No TimeTable Exists",Toast.LENGTH_SHORT).show();
            return -1;}

        return 1;
    }

    private void alertdialogbox(final String line) {
        SharedPreferences preferences = getActivity().getSharedPreferences("student_profile", Context.MODE_PRIVATE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("you wanna delete all you saved data " + "\n" + "includes\n\n" + "1. All your TimeTable\n\n" + "2. All your Attendence"
                            +"\n or \n merge the new TimeTable in Old One ??")
                .setPositiveButton("New TimeTable", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().deleteDatabase(SubDbHelper.DATABASE_NAME);
                        getActivity().deleteDatabase(Check_regularHelper.DATABASE_NAME);
                        getActivity().getSharedPreferences(EarlierRecord.earlier, Context.MODE_PRIVATE).edit().clear().apply();

                        creatingTimetable(line);
                    }
                })
                .setNegativeButton("merge", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        creatingTimetable(line);
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Hey, " + preferences.getString("student_name", ""));
        alertDialog.show();
    }

    private void  creatingTimetable(String line) {
        if(!line.isEmpty()){
            int i;
            String[] subjects = line.split("-");
            if(!subjects[0].equals(check)){
                Toast.makeText(getContext(),"TimeTable is corrupted",Toast.LENGTH_SHORT).show();
                return ;
            }
            String[] subjects_data = null;
            SubDbHelper subDbhelper = new SubDbHelper(getActivity());
            for(i=1;i<subjects.length;i++){
                subjects_data = subjects[i].split(";");
                SQLiteDatabase db = subDbhelper.getWritableDatabase();
                ContentValues contentvalues = new ContentValues();
                contentvalues.put(SubContract.SubEntry.SUB_NAME,subjects_data[0]);
                contentvalues.put(SubContract.SubEntry.SUB_CODE,subjects_data[1]);
                contentvalues.put(SubContract.SubEntry.DAY_MON,subjects_data[2]);
                contentvalues.put(SubContract.SubEntry.DAY_TUE,subjects_data[3]);
                contentvalues.put(SubContract.SubEntry.DAY_WED,subjects_data[4]);
                contentvalues.put(SubContract.SubEntry.DAY_THU,subjects_data[5]);
                contentvalues.put(SubContract.SubEntry.DAY_FRI,subjects_data[6]);
                contentvalues.put(SubContract.SubEntry.DAY_SAT,subjects_data[7]);
                contentvalues.put(SubContract.SubEntry.DAY_SUN,subjects_data[8]);

                long j=db.insert(SubContract.SubEntry.TABLE_NAME,null,contentvalues);
                if(j<0)Toast.makeText(getContext(),subjects_data[1] +"already exists",Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(getContext(),"TimeTable Created Successfully",Toast.LENGTH_SHORT).show();
            switchfragment();
        }else {
            Toast.makeText(getContext(),"Timetable Not Found",Toast.LENGTH_SHORT).show();
            return ;
        }
    }
}
