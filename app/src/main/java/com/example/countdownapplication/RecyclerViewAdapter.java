package com.example.countdownapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> mTimeList = new ArrayList<>();
    private ArrayList<String> mTaskList = new ArrayList<>();
    private ArrayList<String> mDateList = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> timeList, ArrayList<String> taskList,
                               ArrayList<String> dateList, Context context){

        mTaskList = taskList;
        mTimeList = timeList;
        mDateList = dateList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tasklist, parent,
                false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.dateviewTv.setText(mDateList.get(position));
        holder.taskviewTv.setText(mTaskList.get(position));
        holder.timeviewTv.setText(mTimeList.get(position));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mTaskList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView timeviewTv, taskviewTv, dateviewTv;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeviewTv = (TextView)itemView.findViewById(R.id.tv_timeview);
            taskviewTv = (TextView)itemView.findViewById(R.id.tv_taskview);
            dateviewTv = (TextView)itemView.findViewById(R.id.tv_dateview);

            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.layout_relative);


        }
    }
}
