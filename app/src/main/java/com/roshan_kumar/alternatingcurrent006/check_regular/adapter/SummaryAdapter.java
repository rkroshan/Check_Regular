package com.roshan_kumar.alternatingcurrent006.check_regular.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roshan_kumar.alternatingcurrent006.check_regular.R;

import java.util.ArrayList;

/**
 * Created by CREATOR on 9/26/2017.
 */

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {

    Context context;
    ArrayList<show_subjectsummaryData> mlist = new ArrayList<>();

    public SummaryAdapter(Context mcontext,ArrayList<show_subjectsummaryData> list){
        context=mcontext;
        mlist=list;
    }
    @Override
    public SummaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subject_summary,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SummaryAdapter.ViewHolder holder, int position) {
        show_subjectsummaryData data = mlist.get(position);
        holder.date.setText(data.getDate());
        holder.status.setText(data.getStatus());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,status;
        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
