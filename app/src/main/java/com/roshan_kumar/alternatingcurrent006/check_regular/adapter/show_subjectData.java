package com.roshan_kumar.alternatingcurrent006.check_regular.adapter;

/**
 * Created by CREATOR on 8/20/2017.
 */

public class show_subjectData {
    String sub_name;
    String sub_code;
    String time;


    public show_subjectData(){
         }

    public void setSub_name(String name){
        this.sub_name=name;
    }
    public void setSub_code(String code){
        this.sub_code=code;
    }
    public String getSub_name(){
        return sub_name;
    }
    public String getSub_code(){
        return sub_code;
    }
    public String gettime() {return time;}
    public void settime(String time) {this.time = time; }
}
